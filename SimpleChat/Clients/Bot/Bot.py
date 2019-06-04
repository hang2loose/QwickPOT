import json

import socketio

import Clients.Bot.HelloBot

sio = socketio.Client()
bot = Clients.Bot.HelloBot.HelloBot("localhost")


@sio.event
def connect():
  print('connection established')


@sio.event
def my_message(data):
  print('message received with ', data)
  sio.emit('my response', {'response': 'my response'})


@sio.event
def disconnect():
  print('disconnected from server')


@sio.on('message')
def handle_bot(message):
  print("message recived: {}".format(message))
  a_message = bot.get_msg(message["message"])
  print("extracted message: {}".format(a_message))
  sio.emit('message_bot', create_message(a_message))


def create_message(message: str):
  tmp_dict = {
    "username": "bot",
    "message": message
  }
  return json.dumps(tmp_dict)


sio.connect('http://localhost:8080')
sio.wait()
