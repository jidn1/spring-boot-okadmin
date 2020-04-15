layui.use(["element","form","layer","okMock"], function () {
    let okMock = layui.okMock;
    var $ = layui.jquery;

    const recordBtn = document.querySelector(".record-btn");
    const player = document.querySelector(".audio-player");

    if (navigator.mediaDevices.getUserMedia) {
        var chunks = [];
        const constraints = { audio: true };
        navigator.mediaDevices.getUserMedia(constraints).then(
            stream => {

                const mediaRecorder = new MediaRecorder(stream);
                $("body").on("click", ".record-btn", function (e) {
                //recordBtn.onclick = () => {

                    if (mediaRecorder.state === "recording") {
                        mediaRecorder.stop();
                        e.text = "开始录音";
                    } else {
                        mediaRecorder.start();
                        e.text = "结束录音";
                    }
                });

                mediaRecorder.ondataavailable = e => {
                    chunks.push(e.data);
                    var formData = new FormData();
                    formData.append("file",e.data);
                    $.ajax({
                        url: okMock.api.baseUrl +'/chat/chatMp3',
                        type:'POST',
                        cache: false,
                        processData: false,
                        contentType: false,
                        data: formData,
                        success: function(data) {
                            console.log("data.data.src"+data.data.src)
                            sendaudio(data.data.src);
                            layer.closeAll();
                        }
                    });
                };

                mediaRecorder.onstop = e => {
                    var blob = new Blob(chunks, { type: "audio/ogg; codecs=opus" });
                    chunks = [];
                    var audioURL = window.URL.createObjectURL(blob);
                    console.log($("body").find(".audio-player").html())
                    $("body").find(".audio-player").attr("src",audioURL);
                    console.log("audioURL="+audioURL)
                    //player.src = audioURL;
                };
            },
            () => {
                console.error("授权失败！");
            }
        );
    } else {
        console.error("浏览器不支持 getUserMedia");
    }

})

