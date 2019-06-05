FROM python:3

WORKDIR /usr/src/app

EXPOSE 8080

RUN pip install python-socketio
RUN pip install eventlet

COPY WebSocketServer.py .

CMD [ "python", "./WebSocketServer.py" ]