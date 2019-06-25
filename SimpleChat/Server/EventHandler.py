import socketio


class EventHandler:
    sio = socketio.Server()

    @sio.event
    def disconnect(self):
        disconnect_message = {
            "ID": self,
            "event_type": "user_disconnected"
        }

        EventHandler.sio.emit('user_disconnected', disconnect_message)
        print("Sent: " + str(disconnect_message))

    @sio.on('user_send')
    def echo_message(sid, message):
        print('Received event: {} from user: {}'.format(message, sid))
        EventHandler.sio.emit('user_receive', message, sid)
        message.update({"ID": sid})
        EventHandler.sio.emit('bot_receive', message)

    @sio.on('user_on_connect')
    def connect_msg(sid, message):
        print('User connected: ', sid)
        message.update({"ID": sid})
        print(message)
        EventHandler.sio.emit('bot_receive', message)

    @sio.on('bot_send')
    def echo_bot(sid, message):
        print('Sent bot answer: {}'.format(message))
        EventHandler.sio.emit('user_receive', message, message.pop("ID"))
