import json

import socketio


class EventHandler:
    sio = socketio.Server()
    users = []

    @sio.event
    def connect(sid, environ):
        print('User connected: ', sid)
        EventHandler.users.append(sid)
        print(str(EventHandler.users))
        # Send Msg to bot that user has connected, return welcome message

    @sio.event
    def disconnect(sid):
        print('User disconnected: ', sid)
        EventHandler.users.remove(sid)
        print(str(EventHandler.users))
        # Send Msg to bot that user has disconnected, "unregister" bot

    @sio.on('message')
    def echo_message(sid, message):
        print('Received event: {} from user: {}'.format(message, sid))
        EventHandler.sio.emit('message', message)

    @sio.on('message_bot')
    def echo_bot(sid, message):
        json_string = json.loads(message)
        if json_string["message"] is not None:
            print('Sent bot answer: {}'.format(message))
            EventHandler.sio.emit('message_bot', message)
