// 如果需要，生成随机的房间名称
if (!location.hash) {
    location.hash = Math.floor(Math.random() * 0xFFFFFF).toString(16);
}
const roomHash = location.hash.substring(1);

console.log(roomHash)
// 用您自己的通道ID替换
const drone = new ScaleDrone('yiS12Ts5RdNhebyM');
//房间名称前须加上“可观察到的-”
const roomName = 'observable-' + roomHash;
const configuration = {
    iceServers: [{
        urls: 'stun:stun.l.google.com:19302'
    }]
};
let room;
let pc;


function onSuccess() {};
function onError(error) {
    console.error(error);
};

drone.on('open', error => {
    if (error) {
        return console.error(error);
    }
    room = drone.subscribe(roomName);
    room.on('open', error => {
        if (error) {
            onError(error);
        }
    });
    // 我们连接到房间并接收到一组“成员”
    // 连接到房间(包括我们)。信令服务器准备好了。
    room.on('members', members => {
        console.log('MEMBERS', members);
        // 如果我们是第二个连接到房间的用户，我们将创建offer
        const isOfferer = members.length === 2;
        startWebRTC(isOfferer);
    });
});

// 通过Scaledrone发送信号数据
function sendMessage(message) {
    drone.publish({
        room: roomName,
        message
    });
}

function startWebRTC(isOfferer) {
    pc = new RTCPeerConnection(configuration);

    // “onicecandidate”在ICE代理需要交付a时通知我们
    // 通过信令服务器向另一个对等点发送消息
    pc.onicecandidate = event => {
        if (event.candidate) {
            sendMessage({'candidate': event.candidate});
        }
    };

    // If user is offerer let the 'negotiationneeded' event create the offer
    if (isOfferer) {
        pc.onnegotiationneeded = () => {
            pc.createOffer().then(localDescCreated).catch(onError);
        }
    }

    // 当远程流到达时，将其显示在#remoteVideo元素中
    pc.ontrack = event => {
        const stream = event.streams[0];
        if (!remoteVideo.srcObject || remoteVideo.srcObject.id !== stream.id) {
            remoteVideo.srcObject = stream;
        }
    };

    navigator.mediaDevices.getUserMedia({
        audio: true,
        video: true,
    }).then(stream => {
        // 在#localVideo元素中显示本地视频
        localVideo.srcObject = stream;
        // 添加要发送到conneting对等点的流
        stream.getTracks().forEach(track => pc.addTrack(track, stream));
    }, onError);

    // 听Scaledrone的信号数据
    room.on('data', (message, client) => {
        // 消息是由我们发出的
        if (client.id === drone.clientId) {
            return;
        }

        if (message.sdp) {
            // 这是在收到来自其他同事的提议或回答后调用的
            pc.setRemoteDescription(new RTCSessionDescription(message.sdp), () => {
                // 当收到offer时，让我们答复
                if (pc.remoteDescription.type === 'offer') {
                    pc.createAnswer().then(localDescCreated).catch(onError);
                }
            }, onError);
        } else if (message.candidate) {
            // 将新的ICE候选项添加到我们的连接远程描述中
            pc.addIceCandidate(
                new RTCIceCandidate(message.candidate), onSuccess, onError
            );
        }
    });
}

function localDescCreated(desc) {
    pc.setLocalDescription(
        desc,
        () => sendMessage({'sdp': pc.localDescription}),
        onError
    );
}
