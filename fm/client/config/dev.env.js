'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://10.1.1.116:9999/api"'
  // BASE_API: '"http://localhost:5000"',
  // BASE_API: '"http://192.168.0.105:8099"',
  // BASE_API: '"http://61.172.179.26:5000"',
  // BASE_API: '"/api"',	//proxy table启用时，只需要指定访问的路径
  // WEB_SOCKET_URL:'"ws://127.0.0.1:9999/websocket/"',
  // WEB_SOCKET_URL:'"ws://61.172.179.26:5000/fm-back-test/websocket/"',
  BASE_API: '"http://localhost:5000"',
  // BASE_API: '"/api"',	//proxy table启用时，只需要指定访问的路径
  WEB_SOCKET_URL:'"ws://127.0.0.1:5000/websocket/"',

  // 报表地址配置
   REPORT_CLIENT_URL: '"http://localhost:9528/"',
   REPORT_SERVER_URL: '"http://localhost:8081/"',
//  REPORT_CLIENT_URL: '"http://59.80.34.149:8099/dataease-dev/frontend/"',
//  REPORT_SERVER_URL: '"http://59.80.34.149:8099/dataease-dev/backend/"',

  // 文件在线预览地址
  FILE_PREVIEW_URL: '"http://377328t1x9.zicp.vip:52265/"',
  // kkFile在线预览地址
  KK_FILE_URL: '"http://192.168.0.4:8012/onlinePreview?url="',
})
