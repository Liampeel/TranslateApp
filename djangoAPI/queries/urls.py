from django.urls import path
from queries import views
from rest_framework.urlpatterns import format_suffix_patterns
urlpatterns = [
    path('queries/', views.query_list),
    path('queries/<int:pk>/', views.query_detail),
]

urlpatterns = format_suffix_patterns(urlpatterns)
