import socketio


class EventHandler:
    sio = socketio.Server()
    users = []

    @sio.event
    def connect(sid, environ):
        print('User connected: ', sid)
        EventHandler.users.append(sid)
        print(str(EventHandler.users))
        EventHandler.sio.emit('user_connected', sid)

    @sio.event
    def disconnect(sid):
        print('User disconnected: ', sid)
        EventHandler.users.remove(sid)
        print(str(EventHandler.users))
        EventHandler.sio.emit('user_disconnected', sid)

    @sio.on('user_send')
    def echo_message(sid, message):
        print('Received event: {} from user: {}'.format(message, sid))
        EventHandler.sio.emit('user_receive', message, sid)
        message.update({"sid": sid})
        EventHandler.sio.emit('bot_receive', message)

    @sio.on('bot_send')
    def echo_bot(sid, message):
        if message["message"] is not None:
            print('Sent bot answer: {}'.format(message))
            EventHandler.sio.emit('user_receive', message, message.pop("sid"))
