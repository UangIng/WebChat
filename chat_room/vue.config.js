module.exports = {
  publicPath: process.env.NODE_ENV === 'production'
    ? './'
    : '/',
  lintOnSave: false,//关闭eslint验证
  runtimeCompiler: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9009/', //对应自己的接口
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }

}