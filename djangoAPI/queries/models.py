from django.db import models
from pygments.lexers import get_all_lexers
from pygments.styles import get_all_styles
# Create your models here.

class Query(models.Model):
    date_created = models.DateTimeField(auto_now_add=True)
    user = models.ForeignKey('auth.User', related_name='queries', on_delete=models.CASCADE)
    user_query = models.TextField()

    def save(self, *args, **kwargs):
        super(Query, self).save(*args, **kwargs)

    class Meta:
        ordering = ['date_created']
