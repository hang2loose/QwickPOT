import json

import socketio

import SimpleChat.Clients.Bot.HelloBot as HelloBot

sio = socketio.Client()
bot = HelloBot.HelloBot("data-service")


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


@sio.on('bot_receive')
def handle_bot(message):
  print("message received: {}".format(message))
  print("extracted message: {}".format(message["message"]))
  create_message(message)
  print("message to send: {}".format(message))
  sio.emit('bot_send', message)


def create_message(message):
  message["username"] = "🤖 Bot"
  message["message"] = bot.get_msg(message["message"])
  return message


sio.connect('http://localhost:8080')
sio.wait()
