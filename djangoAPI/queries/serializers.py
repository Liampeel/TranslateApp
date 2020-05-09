from rest_framework import serializers
from queries.models import Query


class RegistrationSerializer:
    class Meta:
        fields = ['username', 'email', 'token']

class QuerySerializer(serializers.ModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')
    class Meta:
        model = Query
        fields = ['id', 'owner', 'user_query']


class UserSerializer(serializers.ModelSerializer):
    queries = serializers.PrimaryKeyRelatedField(many=True, queryset=Query.objects.all())

    class meta:
        fields = ['id', 'username', 'queries']
