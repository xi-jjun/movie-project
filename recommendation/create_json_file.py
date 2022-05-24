import json
import csv

# csv 파일을 json 파일로 변환
J = []
with open('movie_list.csv', 'rt', encoding='utf-8') as data_csv:
    csv_reader = csv.DictReader(data_csv)
    for csvrows in csv_reader:
        csvrows["id"] = int(csvrows["id"])
        csvrows["vote_count"] = int(csvrows["vote_count"])
        csvrows["score"] = float(csvrows["score"])
        csvrows["popularity"] = float(csvrows["popularity"])
        J.append(csvrows)

with open('movie.json', 'wt', encoding='utf-8') as data_json:
    data_json.write(json.dumps(J, indent=4, sort_keys=True, ensure_ascii=False))
