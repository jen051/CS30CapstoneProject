import warnings
warnings.filterwarnings("ignore")
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics.pairwise import cosine_similarity
import re
from PIL import Image
import requests
import random
import os
import sys
from sklearn.feature_extraction.text import CountVectorizer
# books_path = os.path.join(sys.path[0], "Books.csv")
# ratings_path = os.path.join(sys.path[0], "Ratings.csv")
# saved_path = os.path.join(sys.path[0], "SavedBooks.csv")
books=pd.read_csv(books_path)
ratings = pd.read_csv(ratings_path)
saved = pd.read_csv(saved_path)
books_data=books.merge(ratings,on="ISBN")
df=books_data.copy()
df.dropna(inplace=True)
df.reset_index(drop=True,inplace=True)
df.drop(columns=["ISBN","Year-Of-Publication","Image-URL-S","Image-URL-M"],axis=1,inplace=True)
df.drop(index=df[df["Book-Rating"]==0].index,inplace=True)
df["Book-Title"]=df["Book-Title"].apply(lambda x: re.sub("[\W_]+"," ",x).strip())
print(df.head())
new_df=df[df['User-ID'].map(df['User-ID'].value_counts()) > 200]  # Drop users who vote less than 200 times.
frame = [new_df,saved]
new_df = pd.concat(frame)
users_pivot=new_df.pivot_table(index=["User-ID"],columns=["Book-Title"],values="Book-Rating")
users_pivot.fillna(0,inplace=True)
print(new_df.head())
print(users_pivot.head())
def user_based(new_df,id):
    index=np.where(users_pivot.index==id)[0][0]
    print(index)
    similarity=cosine_similarity(users_pivot)
    similar_users=list(enumerate(similarity[index]))
    similar_users = sorted(similar_users,key = lambda x:x[1],reverse=True)[0:5]
    user_rec=[]
    for i in similar_users:
            data=df[df["User-ID"]==users_pivot.index[i[0]]]
            user_rec.extend(list(data.drop_duplicates("User-ID")["User-ID"].values))
    return user_rec
def common(new_df,user,user_id):
    x=new_df[new_df["User-ID"]==user_id]
    recommend_books=[]
    user=list(user)
    for i in user:
        y=new_df[(new_df["User-ID"]==i)]
        books=y.loc[~y["Book-Title"].isin(x["Book-Title"]),:]
        books=books.sort_values(["Book-Rating"],ascending=False)[0:5]
        recommend_books.extend(books["Book-Title"].values)
    return recommend_books[0:5]
user_id=0
user_based_rec=user_based(new_df,user_id)
books_for_user=common(new_df,user_based_rec,user_id)
books_for_userDF=pd.DataFrame(books_for_user,columns=["Book-Title"])
print(books_for_user)
for i in range(5):
    print(new_df[new_df["Book-Title"]==books_for_userDF["Book-Title"].tolist()[i]]["Book-Rating"].mean())
    print(new_df[new_df["Book-Title"]==books_for_userDF["Book-Title"].tolist()[i]]["Book-Title"])