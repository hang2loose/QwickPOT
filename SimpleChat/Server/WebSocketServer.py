import eventlet
import socketio

from SimpleChat.Server.EventHandler import EventHandler
from SimpleChat.Server.ServerConfigurator import ServerConfigurator


class WebSocketServer:
    def start_on_port(self, address, port):
        event_handler = EventHandler()

        sio = event_handler.sio

        app = socketio.WSGIApp(sio, static_files={
          '/': {'content_type': 'text/html', 'filename': 'index.html'}
        })

        if __name__ == '__main__':
            eventlet.wsgi.server(eventlet.listen((address, port)), app)


configurator = ServerConfigurator()
lines = configurator.read_file("server-config.yml")

web_Server = WebSocketServer()
web_Server.start_on_port(configurator.get_address(lines), configurator.get_port(lines))
