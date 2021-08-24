package io.openvidu.recording.java;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

// json
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
// import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Recording;
import io.openvidu.java.client.RecordingProperties;
import io.openvidu.java.client.Session;

// unzip
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@RestController
@RequestMapping()
public class MyRestController {

	// OpenVidu object as entrypoint of the SDK
	private OpenVidu openVidu;

	// Collection to pair session names and OpenVidu Session objects
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	// Collection to pair session names and tokens (the inner Map pairs tokens and
	// role associated)
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
	// Collection to pair session names and recording objects
	private Map<String, Boolean> sessionRecordings = new ConcurrentHashMap<>();

	// URL where our OpenVidu server is listening
	private String OPENVIDU_URL;
	// Secret shared with our OpenVidu server
	private String SECRET;

	// room 생성 시 정보
	String sName, nName;
	
	public MyRestController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}

	/*******************/
	/*** Create Room ***/
	/*** @throws URISyntaxException *****************/
	@RequestMapping(value = "/createRoom", method = RequestMethod.GET)
	public ResponseEntity<?> getRoom(@RequestParam Map<String, String> sessionName, @RequestParam Map<String, String> nickName) throws URISyntaxException {
		// 만들 방 정보 받아오기	
		sName = (String) sessionName.get("sessionName");
		nName = (String) nickName.get("nickName");
		System.out.println("sessionName: " + sName + ", nickName: " + nName);
		
		// Json 형태로 저장
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("sessionName", sName);
		json.addProperty("nickName", nName);
		System.out.println(json);
		
		// redirect하면서 데이터 전달
		URI uri = new URI("https://i5a204.p.ssafy.io:5000/");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		httpHeaders.add("sessinInfo", sName + "," + nName);

		return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
	}
	
	/*******************/
	/*** Session API ***/
	/*******************/

	@RequestMapping(value = "/api/get-token", method = RequestMethod.POST)
	public ResponseEntity<JsonObject> getToken(@RequestBody Map<String, Object> sessionNameParam) {

		System.out.println("Getting sessionId and token | {sessionName}=" + sessionNameParam);

		// The video-call to connect ("TUTORIAL")
		String sessionName = (String) sessionNameParam.get("sessionName");

		// Role associated to this user
		OpenViduRole role = OpenViduRole.PUBLISHER;

		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
				.role(role).data("user_data").build();

		JsonObject responseJson = new JsonObject();

		if (this.mapSessions.get(sessionName) != null) {
			// Session already exists
			System.out.println("Existing session " + sessionName);
			try {

				// Generate a new token with the recently created connectionProperties
				String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionNamesTokens.get(sessionName).put(token, role);

				// Prepare the response with the token
				responseJson.addProperty("0", token);

				// Return the response to the client
				return new ResponseEntity<>(responseJson, HttpStatus.OK);

			} catch (OpenViduJavaClientException e1) {
				// If internal error generate an error message and return it to client
				return getErrorResponse(e1);
			} catch (OpenViduHttpException e2) {
				if (404 == e2.getStatus()) {
					// Invalid sessionId (user left unexpectedly). Session object is not valid
					// anymore. Clean collections and continue as new session
					this.mapSessions.remove(sessionName);
					this.mapSessionNamesTokens.remove(sessionName);
				}
			}
		}

		// New session
		System.out.println("New session " + sessionName);
		try {

			// Create a new OpenVidu Session
			Session session = this.openVidu.createSession();
			// Generate a new token with the recently created connectionProperties
			String token = session.createConnection(connectionProperties).getToken();

			// Store the session and the token in our collections
			this.mapSessions.put(sessionName, session);
			this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
			this.mapSessionNamesTokens.get(sessionName).put(token, role);

			// Prepare the response with the sessionId and the token
			responseJson.addProperty("0", token);

			// Return the response to the client
			return new ResponseEntity<>(responseJson, HttpStatus.OK);

		} catch (Exception e) {
			// If error generate an error message and return it to client
			return getErrorResponse(e);
		}
	}

	@RequestMapping(value = "/api/remove-user", method = RequestMethod.POST)
	public ResponseEntity<JsonObject> removeUser(@RequestBody Map<String, Object> sessionNameToken) throws Exception {

		System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

		// Retrieve the params from BODY
		String sessionName = (String) sessionNameToken.get("sessionName");
		String token = (String) sessionNameToken.get("token");

		// If the session exists
		if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

			// If the token exists
			if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
				// User left the session
				if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
					// Last user left: session must be removed
					this.mapSessions.remove(sessionName);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The TOKEN wasn't valid
				System.out.println("Problems in the app server: the TOKEN wasn't valid");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/close-session", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> closeSession(@RequestBody Map<String, Object> sessionName) throws Exception {
		// Retrieve the param from BODY
		String session = (String) sessionName.get("sessionName");
		
		System.out.println("Closing session | {sessionName}=" + session);

		// If the session exists
		if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
			Session s = this.mapSessions.get(session);
			s.close();
			this.mapSessions.remove(session);
			this.mapSessionNamesTokens.remove(session);
			this.sessionRecordings.remove(s.getSessionId());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/fetch-info", method = RequestMethod.POST)
	public ResponseEntity<JsonObject> fetchInfo(@RequestBody Map<String, Object> sessionName) {
		try {
			System.out.println("Fetching session info | {sessionName}=" + sessionName);

			// Retrieve the param from BODY
			String session = (String) sessionName.get("sessionName");

			// If the session exists
			if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
				Session s = this.mapSessions.get(session);
				boolean changed = s.fetch();
				System.out.println("Any change: " + changed);
				return new ResponseEntity<>(this.sessionToJson(s), HttpStatus.OK);
			} else {
				// The SESSION does not exist
				System.out.println("Problems in the app server: the SESSION does not exist");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}

	@RequestMapping(value = "/api/fetch-all", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAll() {
		try {
			System.out.println("Fetching all session info");
			boolean changed = this.openVidu.fetch();
			System.out.println("Any change: " + changed);
			JsonArray jsonArray = new JsonArray();
			for (Session s : this.openVidu.getActiveSessions()) {
				jsonArray.add(this.sessionToJson(s));
			}
			return new ResponseEntity<>(jsonArray, HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}

	@RequestMapping(value = "/api/force-disconnect", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceDisconnect(@RequestBody Map<String, Object> params) {
		try {
			// Retrieve the param from BODY
			String session = (String) params.get("sessionName");
			String connectionId = (String) params.get("connectionId");

			// If the session exists
			if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
				Session s = this.mapSessions.get(session);
				s.forceDisconnect(connectionId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The SESSION does not exist
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}

	@RequestMapping(value = "/api/force-unpublish", method = RequestMethod.DELETE)
	public ResponseEntity<JsonObject> forceUnpublish(@RequestBody Map<String, Object> params) {
		try {
			// Retrieve the param from BODY
			String session = (String) params.get("sessionName");
			String streamId = (String) params.get("streamId");

			// If the session exists
			if (this.mapSessions.get(session) != null && this.mapSessionNamesTokens.get(session) != null) {
				Session s = this.mapSessions.get(session);
				s.forceUnpublish(streamId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				// The SESSION does not exist
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			e.printStackTrace();
			return getErrorResponse(e);
		}
	}

	/*******************/
	/** Recording API **/
	/*******************/

	@RequestMapping(value = "/api/recording/start", method = RequestMethod.POST)
	public ResponseEntity<?> startRecording(@RequestBody Map<String, Object> params) {
		String sessionId = (String) params.get("session");
		Recording.OutputMode outputMode = Recording.OutputMode.valueOf((String) params.get("outputMode"));
		boolean hasAudio = (boolean) params.get("hasAudio");
		boolean hasVideo = (boolean) params.get("hasVideo");

		RecordingProperties properties = new RecordingProperties.Builder().outputMode(outputMode).hasAudio(hasAudio)
				.hasVideo(hasVideo).build();

		System.out.println("Starting recording for session " + sessionId + " with properties {outputMode=" + outputMode
				+ ", hasAudio=" + hasAudio + ", hasVideo=" + hasVideo + "}");

		try {
			Recording recording = this.openVidu.startRecording(sessionId, properties);
			// url 확인 출력
			System.out.println("start recording: " + recording.getUrl());
			this.sessionRecordings.put(sessionId, true);
			return new ResponseEntity<>(recording, HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/recording/stop", method = RequestMethod.POST)
	public ResponseEntity<?> stopRecording(@RequestBody Map<String, Object> params) throws IOException {
		String recordingId = (String) params.get("recording");
		String connectionId = (String) params.get("connectionId");
		
		System.out.println("Stoping recording | {recordingId}=" + recordingId);
		System.out.println("Stoping recording | {connectionId}=" + connectionId);
		
		try {
			Recording recording = this.openVidu.stopRecording(recordingId);
			System.out.println("stop recording - url: " + recording.getUrl());

			String sessionId = recording.getSessionId();
			System.out.println("stop recording - sessionid: " + sessionId);
			
			// upzip
			String zipPath = File.separator + "opt" + File.separator + "openvidu" + File.separator + "recordings" + File.separator + sessionId + File.separator + sessionId + ".zip";
			File zipFile = new File(zipPath);
			// 압축 해제 위치 = 해제한 파일 위치 >> 그래야 openvidu에서 제공하는 delete 기능 사용
			String upzipDir = File.separator + "opt" + File.separator + "openvidu" + File.separator + "recordings" + File.separator + sessionId + File.separator;
			
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
			ZipEntry ze = zis.getNextEntry();
			
			while(ze!=null){
					String entryName = ze.getName();
					System.out.print("Extracting " + entryName + " -> " + upzipDir + File.separator +  entryName + "...");
					File f = new File(upzipDir + File.separator +  entryName);
					//create all folder needed to store in correct relative path.
					f.getParentFile().mkdirs();
					FileOutputStream fos = new FileOutputStream(f);
					int len;
					byte buffer[] = new byte[1024];
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();  
					System.out.println("OK!");
					ze = zis.getNextEntry();         
			}
			zis.closeEntry();
			zis.close();

			// 녹화본 정보가 담긴 json 파일 읽기
			Gson gson = new Gson();
			String jsonPath = File.separator + "opt" + File.separator + "openvidu" + File.separator + "recordings" + File.separator + sessionId + File.separator + sessionId + ".json";
			JsonReader recordJson = new JsonReader(new FileReader(jsonPath));
			JsonObject jsonObject = gson.fromJson(recordJson, JsonObject.class);
			System.out.println(jsonObject.get("files"));
			
			// files 읽어서 params와 같은 connectionId를 갖는 name 가져오기
			JsonArray recordArray = (JsonArray) jsonObject.get("files");
			String recordName = "";
			for(int i = 0; i < recordArray.size(); i++) {
				JsonObject recordFile = (JsonObject) recordArray.get(i);
				System.out.println("recordFile: " + recordFile);
				String curConnectionId = recordFile.get("connectionId").toString().replace("\"", "");
				System.out.println("curConnectionId: " + curConnectionId);
				System.out.println(connectionId.equals(curConnectionId));
				if(connectionId.equals(curConnectionId)) {
					recordName = (String)recordFile.get("name").toString().replace("\"", "");
					System.out.println(recordName);
					break;
				}
			}
			
			// 사용자 녹화본 url로 변환
			String uRecordUrl = "https:" + File.separator + File.separator + "i5a204.p.ssafy.io" + File.separator + 
					"openvidu" + File.separator + "recordings" + File.separator + sessionId + File.separator + recordName;
			System.out.println("uRecordUrl: " + uRecordUrl);
			
			RecordUrlDto urlDto = new RecordUrlDto(recording, uRecordUrl);
			this.sessionRecordings.remove(recording.getSessionId());

			return new ResponseEntity<>(urlDto, HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/recording/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRecording(@RequestBody Map<String, Object> params) {
		String recordingId = (String) params.get("recording");

		System.out.println("Deleting recording | {recordingId}=" + recordingId);

		try {
			this.openVidu.deleteRecording(recordingId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/recording/get/{recordingId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRecording(@PathVariable(value = "recordingId") String recordingId) {

		System.out.println("Getting recording | {recordingId}=" + recordingId);

		try {
			Recording recording = this.openVidu.getRecording(recordingId);
			// url 확인 출력
			System.out.println("get recording: " + recording.getUrl());
			return new ResponseEntity<>(recording, HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/api/recording/list", method = RequestMethod.GET)
	public ResponseEntity<?> listRecordings() {

		System.out.println("Listing recordings");

		try {
			List<Recording> recordings = this.openVidu.listRecordings();

			return new ResponseEntity<>(recordings, HttpStatus.OK);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<JsonObject> getErrorResponse(Exception e) {
		JsonObject json = new JsonObject();
		json.addProperty("cause", e.getCause().toString());
		json.addProperty("error", e.getMessage());
		json.addProperty("exception", e.getClass().getCanonicalName());
		return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected JsonObject sessionToJson(Session session) {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("sessionId", session.getSessionId());
		json.addProperty("customSessionId", session.getProperties().customSessionId());
		json.addProperty("recording", session.isBeingRecorded());
		json.addProperty("mediaMode", session.getProperties().mediaMode().name());
		json.addProperty("recordingMode", session.getProperties().recordingMode().name());
		json.add("defaultRecordingProperties",
				gson.toJsonTree(session.getProperties().defaultRecordingProperties()).getAsJsonObject());
		JsonObject connections = new JsonObject();
		connections.addProperty("numberOfElements", session.getConnections().size());
		JsonArray jsonArrayConnections = new JsonArray();
		session.getConnections().forEach(con -> {
			JsonObject c = new JsonObject();
			c.addProperty("connectionId", con.getConnectionId());
			c.addProperty("role", con.getRole().name());
			c.addProperty("token", con.getToken());
			c.addProperty("clientData", con.getClientData());
			c.addProperty("serverData", con.getServerData());
			JsonArray pubs = new JsonArray();
			con.getPublishers().forEach(p -> {
				pubs.add(gson.toJsonTree(p).getAsJsonObject());
			});
			JsonArray subs = new JsonArray();
			con.getSubscribers().forEach(s -> {
				subs.add(s);
			});
			c.add("publishers", pubs);
			c.add("subscribers", subs);
			jsonArrayConnections.add(c);
		});
		connections.add("content", jsonArrayConnections);
		json.add("connections", connections);
		return json;
	}

}
