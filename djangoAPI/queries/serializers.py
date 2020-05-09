from rest_framework import serializers
from queries.models import Query

class QuerySerializer(serializers.ModelSerializer):
    class Meta:
        model = Query
        fields = ['id', 'user_query']


class UserSerializer(serializers.ModelSerializer):
    queries = serializers.PrimaryKeyRelatedField(many=True, queryset=Query.objects.all())

    class meta:
        fields = ['id', 'username', 'queries']
