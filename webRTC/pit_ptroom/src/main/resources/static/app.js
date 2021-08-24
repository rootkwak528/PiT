var OV
var session

var sessionName
var nickname
var token
var numVideos = 0

// 영상 녹화
var connectionId
var uRecordUrl
var forceRecordingId
var userJson
var userList = []
var nickname
var streamId

var publisher
var isAudioMute = false
var isVideoMute = false

// 수업 시간 체크
let startTime
let endTime

// postMessage
let isTrainer
let classTitle
let classNo

// 비디오 그리드
let colNum = 1
let videoHighlight = false
let focusNum = 0

function muteAudio() {
  if(isAudioMute == true) {
    isAudioMute = false
		publisher.publishAudio(true)
    document.getElementById("muteAudioBtn").innerHTML = `<i class="fas fa-microphone fa-2x"></i>`
    document.getElementById("muteAudioBtn").style.backgroundColor = "#e8e8e8"
    document.getElementById("muteAudioBtn2").innerHTML = `<i class="fas fa-microphone fa-2x"></i>`
    document.getElementById("muteAudioBtn2").style.backgroundColor = "#e8e8e8"
  } else {
    isAudioMute = true
		publisher.publishAudio(false)
    document.getElementById("muteAudioBtn").innerHTML = `<i class="fas fa-microphone-slash fa-2x"></i>`
    document.getElementById("muteAudioBtn").style.backgroundColor = "#f35747"
    document.getElementById("muteAudioBtn2").innerHTML = `<i class="fas fa-microphone-slash fa-2x"></i>`
    document.getElementById("muteAudioBtn2").style.backgroundColor = "#f35747"
  }
}

function muteVideo() {
  if(isVideoMute == true) {
    isVideoMute = false
		publisher.publishVideo(true)
    document.getElementById("muteVideoBtn").innerHTML = `<i class="fas fa-video fa-2x"></i>`
    document.getElementById("muteVideoBtn").style.backgroundColor = "#e8e8e8"
    document.getElementById("muteVideoBtn2").innerHTML = `<i class="fas fa-video fa-2x"></i>`
    document.getElementById("muteVideoBtn2").style.backgroundColor = "#e8e8e8"
  } else {
    isVideoMute = true
		publisher.publishVideo(false)
		document.getElementById("muteVideoBtn").innerHTML = `<i class="fas fa-video-slash fa-2x"></i>`
    document.getElementById("muteVideoBtn").style.backgroundColor = "#6cd8d7"
		document.getElementById("muteVideoBtn2").innerHTML = `<i class="fas fa-video-slash fa-2x"></i>`
    document.getElementById("muteVideoBtn2").style.backgroundColor = "#6cd8d7"
  }
}

window.addEventListener("message", (event) => {
	if (!sessionName) {
		// 버튼 변경
		$('#sessionName').val(event.data.sessionName)
		$('#sessionName').attr('disabled', true)
		$('#nickname').val(event.data.nickname)
		$('#nickname').attr('disabled', true)
		document.getElementById("join-btn").disabled = false

		sessionName = event.data.sessionName
		nickname = event.data.nickname
		isTrainer = event.data.isTrainer
		classTitle = event.data.classTitle
		classNo = event.data.classNo
		
		document.getElementById('classname').innerText = classTitle

		localStorage.setItem('jwt-auth-token', event.data.token)
	}
}, false)

function onClose() {
	if (isTrainer) {
		explodeSession()
	} else {
		leaveSession()
	}
}

/* OPENVIDU METHODS */

function joinSession() {

	// --- 0) Change the button ---
		
	document.getElementById("join-btn").disabled = true
	document.getElementById("join-btn").innerHTML = "Joining..."

	getToken(function () {

		// --- 1) Get an OpenVidu object ---

		OV = new OpenVidu()

		// --- 2) Init a session ---

		session = OV.initSession()

		// --- 3) Specify the actions when events take place in the session ---

		session.on('connectionCreated', event => {
			pushEvent(event)
			connectionId = !connectionId ? event.connection.connectionId : connectionId
			sessionId = event.target.sessionId
		})

		session.on('connectionDestroyed', event => {
			pushEvent(event)
		})

		// On every new Stream received...
		session.on('streamCreated', event => {
			pushEvent(event)

			// video div와 nickname p tag 넣을 videoNode 생성
			const videoContainer = document.getElementById('video-container')
			const videoNode = videoContainer.appendChild(document.createElement("div"))
			videoNode.id = "video-" + event.stream.connection.data.split("%/%")[0]

			// Subscribe to the Stream to receive it
			// HTML video will be appended to element with 'video-container' id
			var subscriber = session.subscribe(event.stream, videoNode.id)

			// When the HTML video has been appended to DOM...
			subscriber.on('videoElementCreated', event => {
				pushEvent(event)
				// Add a new HTML element for the user's name and nickname over its video
				updateNumVideos(1)
				appendNickname(event.element, subscriber.stream.connection)
			})

			// When the HTML video has been appended to DOM...
			subscriber.on('videoElementDestroyed', event => {
				pushEvent(event)
				// Add a new HTML element for the user's name and nickname over its video
				updateNumVideos(-1)
			})

			// When the subscriber stream has started playing media...
			subscriber.on('streamPlaying', event => {
				pushEvent(event)
			})
		})

		session.on('streamDestroyed', event => {
			pushEvent(event)
			removeUserData(event.stream.connection)
		})

		session.on('sessionDisconnected', event => {
			pushEvent(event)
			if (event.reason !== 'disconnect') {
				removeUser()
			}
			if (event.reason !== 'sessionClosedByServer') {
				session = null
				numVideos = 0
				$('#join').show()
				$('#session').hide()
			}
			alert('세션이 종료되었습니다.')
			window.close()	// 트레이너가 세션을 종료했을 때 다른 수강생들의 세션도 종료하고 창닫기
		})

		session.on('recordingStarted', event => {
			pushEvent(event)
		})

		session.on('recordingStopped', event => {
			pushEvent(event)
		})

		// On every asynchronous exception...
		session.on('exception', (exception) => {
			console.warn(exception)
		})

		// --- 4) Connect to the session passing the retrieved token and some more data from
		//        the client (in this case a JSON with the nickname chosen by the user) ---

		session.connect(token, nickname)
			.then(() => {

				// --- 5) Set page layout for active call ---

				$('#session-title').text(sessionName)
				$('#join').hide()
				$('#session').show()

				// --- 6) Get your own camera stream ---

				// video div와 nickname p tag 넣을 videoNode 생성
				const videoContainer = document.getElementById('video-container')
				const videoNode = videoContainer.appendChild(document.createElement("div"))
				videoNode.id = "video-" + nickname

				//publisher = OV.initPublisher('video-container', {
				publisher = OV.initPublisher(videoNode.id, {
					audioSource: undefined, // The source of audio. If undefined default microphone
					videoSource: undefined, // The source of video. If undefined default webcam
					publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
					publishVideo: true, // Whether you want to start publishing with your video enabled or not
					resolution: '640x480', // The resolution of your video
					frameRate: 30, // The frame rate of your video
					insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
					mirror: false // Whether to mirror your local video or not
				})

				// --- 7) Specify the actions when events take place in our publisher ---

				// When the publisher stream has started playing media...
				publisher.on('accessAllowed', event => {
					pushEvent({
						type: 'accessAllowed'
					})
				})

				publisher.on('accessDenied', event => {
					pushEvent(event)
				})

				publisher.on('accessDialogOpened', event => {
					pushEvent({
						type: 'accessDialogOpened'
					})
				})

				publisher.on('accessDialogClosed', event => {
					pushEvent({
						type: 'accessDialogClosed'
					})
				})

				// When the publisher stream has started playing media...
				publisher.on('streamCreated', event => {
					pushEvent(event)
					console.log("publisher: start recording")

					// 트레이너만 녹화 시작
					if (isTrainer) {
						startRecording()
					}

					// DB로 사용자 videoURL 보내기
					streamId = event.stream.streamId
					sendVideoURL()
				})

				// When our HTML video has been added to DOM...
				publisher.on('videoElementCreated', event => {
					pushEvent(event)
					updateNumVideos(1)
					$(event.element).prop('muted', true) // Mute local video
					appendNickname(event.element, nickname)
				})

				// When the HTML video has been appended to DOM...
				publisher.on('videoElementDestroyed', event => {
					pushEvent(event)
					// Add a new HTML element for the user's name and nickname over its video
					updateNumVideos(-1)
				})

				// When the publisher stream has started playing media...
				publisher.on('streamPlaying', event => {
					pushEvent(event)
				})

				// --- 8) Publish your stream ---

				session.publish(publisher)

				document.getElementById('join-container').classList.toggle('d-flex')
				document.getElementById('join-container').style.display = 'none'
				document.getElementById('header').style.display = 'block'
				document.getElementById('settings').style.display = 'block'

				startTime = Date.now()
			})
			.catch(error => {
				console.warn('There was an error connecting to the session:', error.code, error.message)
				enableBtn()
			})

		return false
	})

}

function leaveSession() {
	// --- 9) Leave the session by calling 'disconnect' method over the Session object ---
	session.disconnect()
	enableBtn()
}

function trainerLeaveSesion() {
	return axios ({
		url: '/v1/ptroom/leave/' + classNo,
		baseURL: 'http://localhost:8080/',
		method: 'put',
		headers: {
			Authorization: "Bearer " + localStorage.getItem("jwt-auth-token")
		},
		data: {
		}
	})
}

function postCcnt() {
	return axios ({
		url: '/v1/class/cnt/' + classNo,
		baseURL: 'http://localhost:8080/',
		method: 'put',
		headers: {
			Authorization: "Bearer " + localStorage.getItem("jwt-auth-token")
		},
		data: {
		}
	})
}

function explodeSession() {

	// stopRecording(publisher.connection.connectionId)
	stopRecording()

	// 트레이너가 세션을 나가면 DB에 참가자 0으로 변경하는 ptroom 종료하는 api 
	trainerLeaveSesion()
		.then(res => {
			console.log("Success: DB participants clear")
			
			endTime = Date.now()
			const timeDiff = endTime - startTime
			const timeThreshold = 3_600_000  // 1시간 (ms 단위임)
			
			if (timeDiff > timeThreshold) {
				postCcnt()
					.then(res => {
						console.log("Success: DB count ++")

						closeSession()
					})
					.catch(err => {
						console.log("Fail: DB count update")
						alert('수업이 올바르게 종료되지 않았습니다.\n다시 시도해주세요.')
						closeSession()
					})

			} else {
				closeSession()
			}
		})
		.catch(err => {
			console.log("Fail: clear DB participants")
			alert('수업이 올바르게 종료되지 않았습니다.\n다시 시도해주세요.')
		})
}

/* OPENVIDU METHODS */

function enableBtn (){
	document.getElementById("join-btn").disabled = false
	document.getElementById("join-btn").innerHTML = "Join!"
}

/* APPLICATION REST METHODS */

function getToken(callback) {
	sessionName = $("#sessionName").val() // Video-call chosen by the user

	httpRequest(
		'POST',
		'api/get-token', {
			sessionName: sessionName
		},
		'Request of TOKEN gone WRONG:',
		res => {
			token = res[0] // Get token from response
			console.warn('Request of TOKEN gone WELL (TOKEN:' + token + ')')
			callback(token) // Continue the join operation
		}
	)
}

function appendNickname(videoElement, connection) {
	var userData
	var nodeId
	if (typeof connection === "string") {
		userData = connection
		nodeId = connection
	} else {
		userData = connection.data.split("%/%")[0]
		nodeId = connection.connectionId
	}
	var dataNode = document.getElementById("video-" + userData)
	dataNode.className = "data-node"
	const nickNode = dataNode.appendChild(document.createElement("p"))
	nickNode.innerHTML = userData
} 

function removeUserData(connection) {
	var dataNode = document.getElementById("video-" + connection.data.split("%/%")[0])
	dataNode.parentNode.removeChild(dataNode)
}

function removeUser() {
	httpRequest(
		'POST',
		'api/remove-user', {
			sessionName: sessionName,
			token: token
		},
		'User couldn\'t be removed from session',
		res => {
			console.warn("You have been removed from session " + sessionName)
		}
	)
}

function closeSession() {
	httpRequest(
		'DELETE',
		'api/close-session', {
			sessionName: sessionName
		},
		'Session couldn\'t be closed',
		res => {
			console.warn("Session " + sessionName + " has been closed")
		}
	)
}

function fetchInfo() {
	httpRequest(
		'POST',
		'api/fetch-info', {
			sessionName: sessionName
		},
		'Session couldn\'t be fetched',
		res => {
			console.warn("Session info has been fetched")
		}
	)
}

function fetchAll() {
	httpRequest(
		'GET',
		'api/fetch-all', {},
		'All session info couldn\'t be fetched',
		res => {
			console.warn("All session info has been fetched")
		}
	)
}

function forceDisconnect() {
	httpRequest(
		'DELETE',
		'api/force-disconnect', {
			sessionName: sessionName,
			connectionId: document.getElementById('forceValue').value
		},
		'Connection couldn\'t be closed',
		res => {
			console.warn("Connection has been closed")
		}
	)
}

function forceUnpublish() {
	httpRequest(
		'DELETE',
		'api/force-unpublish', {
			sessionName: sessionName,
			streamId: document.getElementById('forceValue').value
		},
		'Stream couldn\'t be closed',
		res => {
			console.warn("Stream has been closed")
		}
	)
}

function httpRequest(method, url, body, errorMsg, callback) {
	var http = new XMLHttpRequest()
	http.open(method, url, true)
	http.setRequestHeader('Content-type', 'application/json')
	http.addEventListener('readystatechange', processRequest, false)
	http.send(JSON.stringify(body))

	function processRequest() {
		if (http.readyState == 4) {
			if (http.status == 200) {
				try {
					callback(JSON.parse(http.responseText))
				} catch (e) {
					callback(e)
				}
			} else {
				console.warn(errorMsg + ' (' + http.status + ')')
				console.warn(http.responseText)
			}
		}
	}
}


function sendVideoURL() {
	// url 형식: https://i5a204.p.ssafy.io/openvidu/recordings/ses_DDO5OKxePI/str_CAM_E64m_con_TfgYxSzkPB.webm
	axios ({
		url: '/v1/class/video/' + classNo,
		baseURL: 'http://localhost:8080/',
		method: 'post',
		headers: {
			Authorization: "Bearer " + localStorage.getItem("jwt-auth-token")
		},
		data: {
			videoUrl: 'https://i5a204.p.ssafy.io/openvidu/recordings/' + sessionId + '/' + streamId + '.webm',
		}
	})
	.then (res => {
		console.log("Success: send url to DB")
	})
	.catch (err => {
		console.log("Fail: send url to DB")
	})
}

function startRecording() {

	httpRequest(
		'POST',
		'api/recording/start', {
			session: session.sessionId,
			outputMode: "INDIVIDUAL",
			hasAudio: true,
			hasVideo: true
		},
		'Start recording WRONG',
		res => {
			forceRecordingId = res.id
		}
	)
}

function stopRecording() {
	userJson = JSON.stringify(userList)
	httpRequest(
		'POST',
		'api/recording/stop', {
			recording: sessionId,
			connectionId: connectionId,
		},
		'Stop recording WRONG',
		res => {
			uRecordUrl = res.url
		}
	)
}


/* APPLICATION REST METHODS */

/* APPLICATION BROWSER METHODS */

events = ''

window.onbeforeunload = function () { // Gracefully leave session
	if (session) {
		removeUser()

		// X 탭 눌러서 나갔을 때 트레이너, 수강생 별도 처리
		if (isTrainer) {
			closeSession()	// 세션 폭파
		} else {
			leaveSession()	// 수강생만 세션 나가기
		}
	}
}

// 비디오 그리드
function updateNumVideos(i) {
	numVideos += i
	const participantDOM = document.getElementById('participants')
	participantDOM.innerHTML = `<i class="fas fa-users" id="participants">&nbsp&nbsp${numVideos}</i>`

	const winWidth = window.innerWidth
	const winHeight = window.innerHeight

	let rowNum = Math.ceil(numVideos / colNum)
	let videoSizeX = (winWidth - 10 * (colNum - 1)) / colNum
	let totalHeight = rowNum * videoSizeX * 3 / 4 + 10 * (rowNum - 1)

	while (totalHeight > winHeight) {
		colNum += 1
		rowNum = Math.ceil(numVideos / colNum)
		videoSizeX = (winWidth - 10 * (colNum - 1)) / colNum
		totalHeight = rowNum * videoSizeX * 3 / 4 + 10 * (rowNum - 1)
	}

	if (numVideos == 1) {
		colNum = 1
	}

	$('#video-container').css('grid-template-columns', `repeat(${colNum}, 1fr)`)
}

window.onresize = function (event) {
	colNum = 1

	const winWidth = window.innerWidth
	const winHeight = window.innerHeight

	let rowNum = Math.ceil(numVideos / colNum)
	let videoSizeX = (winWidth - 10 * (colNum - 1)) / colNum
	let totalHeight = rowNum * videoSizeX * 3 / 4 + 10 * (rowNum - 1)

	while (totalHeight > winHeight) {
		colNum += 1
		rowNum = Math.ceil(numVideos / colNum)
		videoSizeX = (winWidth - 10 * (colNum - 1)) / colNum
		totalHeight = rowNum * videoSizeX * 3 / 4 + 10 * (rowNum - 1)
	}

	if (numVideos == 1) {
		colNum = 1
	}

	$('#video-container').css('grid-template-columns', `repeat(${colNum}, 1fr)`)
}

// 더블클릭하면 커지기
window.ondblclick = function (event) {
	if (event.target.tagName == 'VIDEO') {
		ondblclickVideo(event.target)
	}
}

function ondblclickVideo(target) {
	containerDOM = document.getElementById('video-container')
	containerFocusDOM = document.getElementById('video-focus-inner-container')
	
	// 타겟 비디오가 하이라이트 비디오라면,
		// 하이라이트 비디오 일반 비디오로 옮기기
	// 타겟 비디오가 일반 비디오라면,
		// 하이라이트 비디오가 이미 있다면,
			// 하이라이트 비디오 일반 비디오로 옮기기
		// 타겟 비디오 하이라이트 비디오로 옮기기

	target = target.parentElement

	if (target.classList.contains('focus')) {
		focusNum -= 1
		const oldChild = containerFocusDOM.removeChild(target)
		const newChild = containerDOM.appendChild(oldChild)
		newChild.classList.toggle('focus')
		containerFocusDOM.style.gridTemplateColumns = '1fr '.repeat(focusNum)

		if (!focusNum) {
			containerFocusDOM.style.height = '0'
			containerDOM.classList.toggle('horizontal-scroll')
			$('video').css('width', '100%')
		}

	} else if (focusNum < 2) {
		focusNum += 1
		const oldChild = containerDOM.removeChild(target)
		const newChild = containerFocusDOM.appendChild(oldChild)
		newChild.classList.toggle('focus')
		containerFocusDOM.style.gridTemplateColumns = '1fr '.repeat(focusNum)

		containerFocusDOM.style.height = '65vh'

		if (focusNum == 1) {
			containerDOM.classList.toggle('horizontal-scroll')
			$('video').css('width', 'auto')
		}

	} else if (focusNum == 2) {
		console.log('impossible')
	}
}

function pushEvent(event) {
	events += (!events ? '' : '\n') + event.type
}

/* APPLICATION BROWSER METHODS */

