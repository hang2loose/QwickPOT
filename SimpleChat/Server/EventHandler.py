import socketio


class EventHandler:
    sio = socketio.Server()

    @sio.event
    def connect(sid, environ):
        print('connect ', sid)

    @sio.event
    def my_message(sid, data):
        print('message ', data)

    @sio.event
    def disconnect(sid):
        print('disconnect ', sid)

    @sio.on('message')
    def echo_message(sid, message):
        print(message)
        EventHandler.sio.emit('message', message)

    @sio.on('message_bot')
    def echo_bot(sid, message):
        if message != '{"username": "bot", "message": null}':
            print(message)
            EventHandler.sio.emit('message_bot', message)
