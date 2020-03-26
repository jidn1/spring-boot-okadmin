import CryptoJS from 'crypto-js';

var cryptoJS = function() {

  //加密
  function encrypt(text){
    var key = CryptoJS.enc.Utf8.parse("mqHnYZdkxrgDmXEm");
    var srcs = CryptoJS.enc.Utf8.parse(text);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
  }

  // 解密
  function decrypt(text){
    var key = CryptoJS.enc.Utf8.parse("mqHnYZdkxrgDmXEm");
    var decrypt = CryptoJS.AES.decrypt(text, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
  }

  return {
    decrypt: decrypt,
    encrypt: encrypt
  }
}();
export default cryptoJS
