import asyncio
import websockets


async def echo(websocket, path):
    try:
        async for message in websocket:
            # send message to bot here using "message"
            # answer = ...
            print(message)

            await websocket.send(message)
            # send answer instead of message
            # await websocket.send(answer)
    except websockets.exceptions.ConnectionClosed:
        print("Client disconnected.")


asyncio.get_event_loop().run_until_complete(
    websockets.serve(echo, 'localhost', 8080))
asyncio.get_event_loop().run_forever()
