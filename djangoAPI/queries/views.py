from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from rest_framework.response import Response
from queries.models import Query
from queries.serializers import QuerySerializer
from django.contrib.sites.shortcuts import get_current_site
from django.shortcuts import render, redirect
from django.utils.encoding import force_bytes
from django.utils.http import urlsafe_base64_encode
from django.template.loader import render_to_string
from django.contrib.auth.models import User
from queries.serializers import UserSerializer
from rest_framework import generics
from rest_framework import permissions
from queries.permissions import IsOwnerOrReadOnly
from django.contrib.auth import authenticate, login, logout
from rest_framework.authtoken.models import Token
from rest_framework.authentication import SessionAuthentication, BasicAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
# Create your views here.
import json

@api_view(['GET'])
def api_root(request, format=None):
    return Response({
        'users': reverse('user-list', request=request, format=format),
        'snippets': reverse('snippet-list', request=request, format=format)
    })


@api_view(['POST'])
def create_user(request, format=None):
    if request.method == 'POST':
        if request.body is not None:
            body_unicode = request.body.decode('utf-8')
            body = json.loads(body_unicode)
            username = body['username']
            email = body['email']
            password = body['password']
            password2 = body['password2']
            if password != password2:
                return HttpResponse('Passwords do not match', status=400)


            user = User.objects.create_user(username=username, email=email, password=password)
            token = Token.objects.create(user=user)
            print(token.key)
            user.save()
            return_data = {"username": username, "email": email, "token": token.key}
            json_return_data_dump = json.dumps(return_data)
            json_return_data = json.loads(json_return_data_dump)
            return Response(json_return_data)
        else:
            return HttpResponse('Invalid request', status=400)
    else:
        return HttpResponse('Invalid request', status=400)

@api_view(['GET'])
def logout_request(request):
    logout(request)
    print(request, "Logged out successfully!")
    return HttpResponse("Logged out successfully")

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def user_login(request):
    if request.method == 'POST':
        if request.body is not None:
            body_unicode = request.body.decode('utf-8')
            body = json.loads(body_unicode)
            username = body['username']
            password = body['password']
            user = authenticate(username=username, password=password)
            if user:
                if user.is_active:
                    login(request, user)
                    return HttpResponse("Succesfully logged in")
                else:
                    return HttpResponse("Your account was inactive.")
            else:
                print("Someone tried to login and failed.")
                print("They used username: {} and password: {}".format(username, password))
                return HttpResponse("Invalid login details given")
        else:
            return HttpResponse('Invalid')
    else:
        return HttpResponse('Invalid')





class QueryList(generics.ListCreateAPIView):
    queryset = Query.objects.all()
    serializer_class = QuerySerializer
    permission_classes = [permissions.IsAuthenticated, IsOwnerOrReadOnly]
    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class QueryDetail(generics.RetrieveUpdateDestroyAPIView):
    queryset = Query.objects.all()
    permission_classes = [permissions.IsAuthenticated, IsOwnerOrReadOnly]
    serializer_class = QuerySerializer

class UserList(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer


class UserDetail(generics.RetrieveAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
