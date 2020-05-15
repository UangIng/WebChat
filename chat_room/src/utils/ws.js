import { Message } from "element-ui"
const WSS_URL = `ws://localhost:9009/ws`
let Socket = ''
let setIntervalWesocketPush = null

/**建立连接 */
export function createSocket() {

    if (!Socket) {
        console.log('建立websocket连接')
        Socket = new WebSocket(WSS_URL)
        Socket.onopen = onopenWS
        Socket.onmessage = onmessageWS
        Socket.onerror = onerrorWS
        Socket.onclose = oncloseWS
        return Socket;
    } else {
        // console.log('websocket已连接')
        Message({
            message: '已连接',
            type: 'success'
        })
    }
}
/**打开WS之后发送心跳 */
export function onopenWS() {
    console.log("websocket已连接")
    // sendPing() //发送心跳
}

/**连接失败重连 */
export function onerrorWS() {
    clearInterval(setIntervalWesocketPush)
    Socket.close()
    createSocket() //重连
}

/**WS数据接收统一处理 */
export function onmessageWS(e) {
    window.dispatchEvent(new CustomEvent('onmessageWS', {
        detail: {
            data: e
        }
    }))
}

/**发送数据
 * @param eventType
 */
export function sendWSPush(content, from, to) {
    const obj = {
        content: content,
        from: from,
        to: to,
        time: getDateFull(),
    }

    if (Socket !== null && Socket.readyState === 3) {
        Socket.close()
        createSocket() //重连
    } else if (Socket.readyState === 1) {
        Socket.send(JSON.stringify(obj))
    } else if (Socket.readyState === 0) {
        setTimeout(() => {
            console.log("Push Msg");
            Socket.send(JSON.stringify(obj))
        }, 3000)
    } else if (Socket.readyState === 3) {
        Message.error("连接已关闭")
    }
    // 0 ：对应常量CONNECTING (numeric value 0)，
    // 正在建立连接连接，还没有完成。The connection has not yet been established.
    // 1 ：对应常量OPEN (numeric value 1)，
    // 连接成功建立，可以进行通信。The WebSocket connection is established and communication is possible.
    // 2 ：对应常量CLOSING (numeric value 2)
    // 连接正在进行关闭握手，即将关闭。The connection is going through the closing handshake.
    // 3 : 对应常量CLOSED (numeric value 3)
    // 连接已经关闭或者根本没有建立。The connection has been closed or could not be opened.
}

/**关闭WS */
export function oncloseWS() {
    clearInterval(setIntervalWesocketPush)
    console.log('websocket已断开')
}


/**发送心跳 */
export function sendPing() {
    let ping = {
        content: "ping"
    }
    Socket.send(JSON.stringify(ping))
    setIntervalWesocketPush = setInterval(() => {

        Socket.send(JSON.stringify(ping))
    }, 5000)
}


function getDateFull() {
    var date = new Date();
    var currentdate = date.getFullYear() + "-" + appendZero(date.getMonth() + 1) + "-" + appendZero(date.getDate()) + " " + appendZero(date.getHours()) + ":" + appendZero(date.getMinutes()) + ":" + appendZero(date.getSeconds());
    return currentdate;
}

function appendZero(s) { return ("00" + s).substr((s + "").length); }  //补0函数
