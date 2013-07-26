package org.carracoo.beans;

import org.carracoo.beans.models.CommentModel;
import org.carracoo.beans.models.PostModel;
import org.carracoo.beans.models.UserModel;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/23/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelPerformanceTest {
	private static final int    size  = 1;

	private static final SomeThread[] threads = new SomeThread[size];
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		for(int i=0;i<size;i++){
			threads[i]=new SomeThread(i);
			threads[i].start();
		}
	}

	public static class SomeThread extends Thread {
		private final int    size  = 2000000;
		private final PostModel[] posts = new PostModel[size];
		private final int index;

		public SomeThread(int index){
			this.index = index;
		}
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			for(int t=0;t<size;t++){
				String i = index+"-"+t;
				UserModel owner = new UserModel();
				owner.id.set("user-"+i+"-0");
				owner.name.set("Post "+i+" Owner");
				owner.email.set("user-"+i+"-0@email.com");

				UserModel u1 = new UserModel();
				u1.id.set("user-"+i+"-1");
				u1.name.set("Post "+i+" Commenter 1");
				u1.email.set("user-"+i+"-1@email.com");

				UserModel u2 = new UserModel();
				u2.id.set("user-"+i+"-2");
				u2.name.set("Post "+i+" Commenter 2");
				u2.email.set("user-"+i+"-2@email.com");

				UserModel u3 = new UserModel();
				u3.id.set("user-"+i+"-3");
				u3.name.set("Post "+i+" Commenter 3");
				u3.email.set("user-"+i+"-3@email.com");

				CommentModel c1 = new CommentModel();
				c1.message.set("Comment 1");
				c1.author.set(u1);

				CommentModel c2 = new CommentModel();
				c1.message.set("Comment 2");
				c1.author.set(u2);

				CommentModel c3 = new CommentModel();
				c1.message.set("Comment 3");
				c1.author.set(u3);

				PostModel post = new PostModel();
				post.id.set("post-"+1);
				post.title.set("Post Title "+i);
				post.owner.set(owner);
				post.comments.set(c1,c2,c3);
				posts[t] = post;
			}
			System.out.println("Thread Time "+(System.currentTimeMillis()-start));
		}
	}
}
