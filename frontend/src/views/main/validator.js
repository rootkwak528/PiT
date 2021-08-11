export function validateEmail(rules, value, callback) {
  const email = value;
  const emailTest = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

  console.log("이메일 발리 들어옴!!!!!!! " + value);
  if (emailTest.test(email) == false) {
    callback(new Error("이메일 형식이 올바르지 않습니다."));
  } else {
    callback();
  }
}

export function validatePwd(rule, value, callback) {
  //console.log(callback);
  const num = value.search(/[0-9]/g);
  const eng = value.search(/[a-z]/gi);
  const spe = value.search(/[^`~!@$!%*#^?&\\(\\)\-_=+]/gi);

  if (value === "") {
    callback(new Error("비밀번호를 입력해 주세요."));
  } else if (value.length < 9) {
    callback(new Error("최소 9 글자를 입력해야 합니다."));
  } else if (value.length > 16) {
    callback(new Error("최대 16자까지 입력 가능합니다."));
  } else if (num < 0 || eng < 0 || spe < 0) {
    callback(new Error("비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다."));
  } else {
    callback();
  }
}

export function validateName(rule, value, callback) {
  if (value === "") {
    callback(new Error("이름을 입력해주세요"));
  } else if (value.length > 30) {
    callback(new Error("최대 30자까지 입력 가능합니다."));
  } else {
    callback();
  }
}

export function validateNickname(rule, value, callback) {
  if (value === "") {
    callback(new Error("P;T에서 사용하실 닉네임을 입력해주세요."));
  } else if (value.length > 10) {
    callback(new Error("최대 10자까지 입력 가능합니다."));
  } else {
    callback();
  }
}

export function validateGender(rule, value, callback) {
  if (value === "") {
    callback(new Error("성별을 선택해주세요."));
  } else {
    callback();
  }
}

export function validatePhone(rule, value, callback) {
  const number = /^[0-9]*$/;
  if (value === "") {
    callback(new Error("휴대전화번호를 입력해주세요."));
  } else if (!number.test(value)) {
    callback(new Error("숫자만 입력해주세요."));
  } else if (value.length != 11) {
    callback(new Error("휴대전화번호 11자리를 입력해주세요."));
    // 숫자만 들어오게 조건 추가
  } else {
    callback();
  }
}
export function validateType(rule, value, callback) {
  //if (state.form.type == "002")
  if (value === "") {
    callback(new Error("트레이너는 필수 입력입니다."));
  } else if (value.length > 200) {
    callback(new Error("최대 200자까지 입력 가능합니다."));
  } else {
    callback();
  }
}
export function validateDesc(rule, value, callback) {
  //if (state.form.type == "002")
  if (value === "") {
    callback(new Error("트레이너는 필수 입력입니다."));
  } else if (value.length > 200) {
    callback(new Error("최대 200자까지 입력 가능합니다."));
  } else {
    callback();
  }
}
