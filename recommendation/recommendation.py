import pandas as pd
from ast import literal_eval
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
pd.set_option('display.max_rows', None)
pd.set_option('display.max_colwidth', None)

movie = pd.read_csv('movie_list.csv')

movies_df = movie[['id', 'genres', 'title', 'vote_count', 'score']]
movies_df['genres'] = movies_df['genres'].apply(literal_eval)
movies_df['genres_literal'] = movies_df['genres'].apply(lambda x: (' ').join(x))

count_vect = CountVectorizer(min_df=0, ngram_range=(1, 2))
genre_mat = count_vect.fit_transform(movies_df['genres_literal'])

# 코사인 유사도를 이용하여 장르 기반으로 영화별 유사도 계산
genre_sim = cosine_similarity(genre_mat, genre_mat)
genre_sim_sorted_ind = genre_sim.argsort()[:, ::-1]


# vote_count와 vote_average를 이용하여 가중치를 반영한 평점을 계산하는 함수
percentile = 0.6
C = movies_df['score'].mean()
m = movies_df['vote_count'].quantile(percentile)
def weighted_vote_average(record):
    v = record['vote_count']
    R = record['score']
    return ( v/(v+m)*R + (m/(m+v)) * C )
movies_df['weighted_vote'] = movie.apply(weighted_vote_average, axis = 1)

# 유사도가 높은 영화를 추출하고 가중치가 반영된 평점순으로 정렬해서 top_n개의 추천 영화 리스트 반환하는 함수
def find_sim_movie(df, sorted_ind, title_name, top_n = 10):
    title_movie = df[df['title'] == title_name]
    title_index = title_movie.index.values
    similar_indexes = sorted_ind[title_index, :(top_n*2)]
    similar_indexes = similar_indexes.reshape(-1)
    similar_indexes = similar_indexes[similar_indexes != title_index]
    return df.iloc[similar_indexes].sort_values('weighted_vote', ascending=False)[:top_n]

similar_movies = find_sim_movie(movies_df, genre_sim_sorted_ind, '센과 치히로의 행방불명', 10)
print(similar_movies[['title', 'score', 'weighted_vote', 'vote_count']])

