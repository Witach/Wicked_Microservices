from pathlib import Path
import os


def get_files(entries):
    output = []
    for entry in entries.iterdir():
        if str(entry).endswith('.class'):
            continue
        if os.path.isfile(str(entry)):
            output.append(str(entry.joinpath()))
        else:
            output += get_files(Path(str(entry.joinpath())))
    return output

entries = Path('/home/dawid/Documents/projects/SocialApp')
output = get_files(entries)

for file in output:
    f =  open(file, 'r', encoding='latin1')
    xxx = f.readlines()
    found = set()
    for line in xxx:
        if "@Kafka" in line or "requestTopic" in line or "responseTopic" in line:
            found.add(line)
    f.close()
    for y in found:
        print(y)



