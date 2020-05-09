from rest_framework import serializers
from snippets.models import Query

class QuerySerializer(serializers.serializer):
    class Meta:
        model = Query
        fields = ['id', 'user_query']
