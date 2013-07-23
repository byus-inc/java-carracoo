package org.carracoo.beans;

import org.carracoo.beans.models.*;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/23/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanPerformanceTest {
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
		private final PostBean[] posts = new PostBean[size];
		private final int index;

		public SomeThread(int index){
			this.index = index;
		}
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			for(int t=0;t<size;t++){
				String i = index+"-"+t;
				UserBean owner = new UserBean();
				owner.setId("user-"+i+"-0");
				owner.setName("Post "+i+" Owner");
				owner.setEmail("user-"+i+"-0@email.com");

				UserBean u1 = new UserBean();
				u1.setId("user-"+i+"-1");
				u1.setName("Post "+i+" Commenter 1");
				u1.setEmail("user-"+i+"-1@email.com");

				UserBean u2 = new UserBean();
				u2.setId("user-"+i+"-2");
				u2.setName("Post "+i+" Commenter 2");
				u2.setEmail("user-"+i+"-2@email.com");

				UserBean u3 = new UserBean();
				u3.setId("user-"+i+"-3");
				u3.setName("Post "+i+" Commenter 3");
				u3.setEmail("user-"+i+"-3@email.com");

				CommentBean c1 = new CommentBean();
				c1.setMessage("Comment 1");
				c1.setAuthor(u1);

				CommentBean c2 = new CommentBean();
				c1.setMessage("Comment 2");
				c1.setAuthor(u2);

				CommentBean c3 = new CommentBean();
				c1.setMessage("Comment 3");
				c1.setAuthor(u3);

				PostBean post = new PostBean();
				post.setId("post-" + 1);
				post.setTitle("Post Title " + i);
				post.setOwner(owner);
				post.setComments(Arrays.asList(c1,c2,c3));
				posts[t] = post;
			}
			System.out.println("Thread Time "+(System.currentTimeMillis()-start));
		}
	}
}
