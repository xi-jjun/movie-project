import requests
import pandas as pd

# 장르 id > name으로 변경을 위해 장르 dictionary 만드는 부분
movie_list = []

url = 'https://api.themoviedb.org/3'
path = '/genre/movie/list'
params = {
    'api_key': '',
    'language': 'ko',
}
response = requests.get(url + path, params=params)
data = response.json()
genre_dict = dict()
for i in data['genres']:
    genre_dict[i['id']] = i['name']

# 필요한 정보를 뽑아서 csv 파일로 변환하는 부분
idx = 0
for i in range(1, 251):
    url = 'https://api.themoviedb.org/3'
    path = '/movie/popular/'
    params = {
        'api_key': '',
        'region': 'KR',
        'language': 'ko',
        'page': str(i),
    }
    response = requests.get(url + path, params=params)
    data = response.json()
    for d in data['results']:
        if not d['poster_path']:
            continue
        poster = 'https://image.tmdb.org/t/p/original' + d['poster_path']
        genre = []
        for g in d['genre_ids']:
            genre.append(genre_dict[g])
        movie_list.append([idx, d['title'], d['overview'], d['vote_average'], d['vote_count'], poster,
                           d['release_date'], d['popularity'], genre])
        idx += 1

data = pd.DataFrame(movie_list, columns=['id', 'title', 'description', 'score', 'vote_count', 'poster_path_url',
                                         'release_date', 'popularity', 'genres'])
data = data.set_index('id')
data.to_csv('movie_list.csv', mode='w', encoding='utf-8')