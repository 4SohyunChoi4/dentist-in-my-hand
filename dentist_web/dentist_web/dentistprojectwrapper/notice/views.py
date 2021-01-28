from django.shortcuts import render, get_object_or_404, redirect
from .models import Post
from .forms import PostForm

# Create your views here.
def main(request):
    posts = Post.objects
    return render(request, 'notice/main.html', {'posts': posts})

def show(request, post_id):
    post = get_object_or_404(Post, pk = post_id )
    return render(request, 'notice/show.html', {'post': post})

def new(request):
    return render(request, 'notice/new.html')

def postcreate(request):
    if request.method == 'POST':
        form = PostForm(request.POST)
        post = form.save(commit=False)
        post.save()
        return redirect('main')
    else:
        form = PostForm()
        return render(request, 'notice/new.html', {'form':form})

def edit(request):
    return render(request, 'notice/new.html')

def postupdate(request, post_id):
    post = get_object_or_404(Post, pk = post_id)
    if request.method == 'POST':
        form = PostForm(request.POST, instance=post)
        if form.is_valid():
            post = form.save(commit=False)
            post.save()
            return redirect('show', post_id=post.pk)
    else:
        form = PostForm(instance=post)
        return render(request, 'notice/edit.html', {'form':form})

def postdelete(request, post_id):
    post = get_object_or_404(Post, pk = post_id)
    post.delete()
    return redirect('main')
    