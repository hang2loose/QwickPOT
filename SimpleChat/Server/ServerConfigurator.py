class ServerConfigurator:
    def read_file(self, filename):
        try:
            f = open(filename, "r")
            lines = f.readlines()
            f.close()

            lines = [l.strip("\n").strip(" ") for l in lines]
        except FileNotFoundError:
            print("Can't find or open file")
            return
        return lines

    def get_address(self, lines):
        if lines[1].startswith("address"):
            return lines[1].split(":")[1].strip()
        else:
            return "0.0.0.0"

    def get_port(self, lines):
        if lines[2].startswith("port"):
            return int(lines[2].split(":")[1].strip())
        else:
            return "8080"
