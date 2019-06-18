import yaml

class ConfigParser:
    def read_file(self, filename):
        try:
            with open(filename, 'r') as ymlfile:
                cfg = yaml.load(ymlfile, Loader=yaml.FullLoader)
                return cfg
        except FileNotFoundError:
            print("Can't find or open file")
            return

    def get_address(self, lines):
        if lines["server"]["address"]:
            return lines["server"]["address"]
        else:
            return "0.0.0.0"

    def get_port(self, lines):
        if lines["server"]["port"]:
            return lines["server"]["port"]
        else:
            return "8080"
