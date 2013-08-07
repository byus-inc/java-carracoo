package org.carracoo.facebook;

import org.carracoo.facebook.models.Permission;
import org.carracoo.utils.Printer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookClientTest {
	private FacebookClient fb;

	@BeforeClass
	public void init() throws FacebookException {
		 fb = new FacebookClient(
			"310363060413",
			"d3709220feb6bb7a3937613f39ef94c8",
			Permission.parse(new Permission[]{
				Permission.EMAIL,
				Permission.USER_BIRTHDAY,
				Permission.FRIENDS_BIRTHDAY
			}),
			"http://localhost/auth"
		);
	}

	@Test
	public void testLoginUrl() throws FacebookException {
		System.out.println(fb.getLoginUrl());
	}

	@Test
	public void testTestAccountsCreate() throws FacebookException {
		System.out.println(fb.appPermissions);
		System.out.println(fb.appAccessToken);
		List<Object> accounts = fb.getTestAccounts();
		Printer.print(accounts);
	}
}
