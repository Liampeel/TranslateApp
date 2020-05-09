from django.urls import path
from queries import views
from rest_framework.urlpatterns import format_suffix_patterns
urlpatterns = [
    path('queries/', views.QueryList.as_view()),
    path('queries/<int:pk>/', views.QueryDetail.as_view()),
    path('users/', views.UserList.as_view()),
    path('users/<int:pk>/', views.UserDetail.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)
