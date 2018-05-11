// http://jmesnil.net/stomp-websocket/doc/

$(document).ready(function () {
    var client;

    function handleMessage(message) {
        var msg = JSON.parse(message.body);

        if (msg.topic === 'login') {
            debugger;
            $('.users-online').append('<li data-user-id="' + msg.from.id + '">' + msg.from + '</li>');
        } else if (msg.topic === 'logout') {
            debugger;
            alert("logout!");
        } else {
            $('#messages').append('<b>' + msg.from + '</b>: ' + msg.message + '<br>');
        }
    }

    $('#connect').on('click', function () {
        debugger;
        client = Stomp.over(new SockJS('/chat'));
        client.connect({}, function (frame) {
            debugger;
            console.log("connected");
            client.subscribe('/topic/messages', function (message) {
                handleMessage(message);
            });

            var msg = JSON.stringify({
                from: {
                    id: 7,
                    username: $('#name').val()
                }, text: $('#name').val() + ' logged in'
            });

            client.send('/app/chat/login', {}, msg);
        });
    });

    $('#disconnect').on('click', function () {
        if (client != null) {
            client.disconnect();
        }
        client = null;
    });

    $('#send').on('click', function () {
        debugger;
        var msg = JSON.stringify({
            from: {
                id: 8,
                username: $('#name').val()
            }, text: $('#textmsg').val()
        });

        client.send('/app/chat/career', {}, msg);
    });

    $.get('/onlineusers', function (data) {
        renderOnlineUsersList(data);
    });

    function renderOnlineUsersList(data) {
        var onlineUsers = JSON.parse(data);
        var onlineUsersList = $('.users-online');
        var onlineUsersHtml = '';

        debugger;

        for (var onlineUser in onlineUsers) {
            onlineUsersHtml += '<li><a href="/profile/view?id=' + onlineUsers[onlineUser].id + '">' + onlineUsers[onlineUser].username + '</a></li>';
        }

        onlineUsersList.html(onlineUsersHtml);
    }
});