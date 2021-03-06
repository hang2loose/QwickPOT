import eventlet
import socketio

from ConfigParser import ConfigParser
from Server.EventHandler import EventHandler


class WebSocketServer:
    def start_on_port(self, address, port):
        event_handler = EventHandler()

        sio = event_handler.sio

        app = socketio.WSGIApp(sio, static_files={
          '/': {'content_type': 'text/html', 'filename': 'index.html'}
        })

        if __name__ == '__main__':
            eventlet.wsgi.server(eventlet.listen((address, port)), app)


configurator = ConfigParser("server-config.yml")
config = configurator.get_config()

web_Server = WebSocketServer()
web_Server.start_on_port(config["address"], config["port"])
