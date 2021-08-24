
// Vue3 관련 설정 파일
module.exports = {
  devServer: {
    // Invalid Host Error 및 HTTPS 관련
    disableHostCheck: true,
    https: true,

    port: 8083,
    open: true,
    proxy: {
      '/v1': {
        // target: 'https://i5a204.p.ssafy.io/api'
        target: 'http://localhost:8080/'
      }
    },
    historyApiFallback: true,
    hot: true
  },
  css: {
    requireModuleExtension: false // import 시에 경로에 .module 포함 안해도 됨.
  },
  transpileDependencies: [
    'element-plus'
  ],
  lintOnSave: false,
  outputDir: '../backend/src/main/resources/dist'
}
