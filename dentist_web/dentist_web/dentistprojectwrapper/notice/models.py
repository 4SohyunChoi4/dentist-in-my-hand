from django.db import models

# Create your models here.
class Post(models.Model):
    title = models.CharField(max_length=200)
    content = models.TextField()
    created_at = models.DateTimeField(auto_now_add = True) # 만든 날짜, 시각 자동 저장
    updated_at = models.DateTimeField(auto_now = True) # 수정 날짜, 시각 자동 저장

    def __str__(self):
        return self.title
    
    def summary(self):
        return self.content[:20]