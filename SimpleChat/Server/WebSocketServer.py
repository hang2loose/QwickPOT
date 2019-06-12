import eventlet
import socketio

from SimpleChat.Server.EventHandler import EventHandler


class WebSocketServer:
    def start_on_port(self, port):
        event_handler = EventHandler()

        sio = event_handler.sio

        app = socketio.WSGIApp(sio, static_files={
          '/': {'content_type': 'text/html', 'filename': 'index.html'}
        })

        if __name__ == '__main__':
            eventlet.wsgi.server(eventlet.listen(('', port)), app)


web_Server = WebSocketServer()
web_Server.start_on_port(8080)
