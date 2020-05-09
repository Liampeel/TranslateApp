from django.urls import path
from queries import views

urlpatterns = [
    path('queries/', views.query_list),
    path('queries/<int:pk>/', views.query_detail),
]
