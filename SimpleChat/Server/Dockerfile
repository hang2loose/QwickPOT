FROM python:slim

WORKDIR /usr/src/app

EXPOSE 8080

RUN pip install python-socketio
RUN pip install eventlet

COPY WebSocketServer.py .
COPY EventHandler.py ./Server/

CMD [ "python", "./WebSocketServer.py" ]