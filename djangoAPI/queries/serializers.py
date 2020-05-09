from rest_framework import serializers
from queries.models import Query

class QuerySerializer(serializers.Serializer):
    class Meta:
        model = Query
        fields = ['id', 'user_query']
