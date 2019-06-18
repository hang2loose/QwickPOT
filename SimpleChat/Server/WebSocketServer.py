import eventlet
import socketio

from Server.EventHandler import EventHandler
from Server.ServerConfigurator import ServerConfigurator


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
config = configurator.read_file("server-config.yml")

web_Server = WebSocketServer()
web_Server.start_on_port(configurator.get_address(config), configurator.get_port(config))
