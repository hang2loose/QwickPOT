import asyncio
import threading

import websockets


class WebSocketClient:

  async def send_message(self):
    async with websockets.connect('ws://localhost:8080/echo') as websocket:
      while True:
        await websocket.send(input("Message: "))

  async def recive_message(self):
    async with websockets.connect('ws://localhost:8080/echo') as websocket:
      while True:
        response = await websocket.recv()
        print(response)

  def __init__(self):
    asyncio.get_event_loop().run_until_complete(self.send_message())
    input_thread = threading.Thread(target=self.send_message)
    input_thread.daemon = True
    input_thread.start()


client = WebSocketClient()
