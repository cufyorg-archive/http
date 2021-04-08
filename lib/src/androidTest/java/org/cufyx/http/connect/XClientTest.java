package org.cufyx.http.connect;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.cufy.http.middleware.OkHttpMiddleware;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("JUnitTestNG")
@RunWith(AndroidJUnit4.class)
public class XClientTest {
	@SuppressWarnings("JUnitTestMethodWithNoAssertions")
	public static Thread getUIThread(Context context) throws InterruptedException {
		Object lock = new Object();
		synchronized (lock) {
			Thread[] uiThread = new Thread[1];

			new Handler(context.getMainLooper()).post(() -> {
				synchronized (lock) {
					//noinspection CallToNativeMethodWhileLocked
					uiThread[0] = Thread.currentThread();
					lock.notifyAll();
				}
			});

			//noinspection WaitNotInLoop,WaitOrAwaitWithoutTimeout,UnconditionalWait
			lock.wait();
			return uiThread[0];
		}
	}

	@SuppressWarnings("MigrateAssertToMatcherAssert")
	@Test
	public void useAppContext() throws InterruptedException {
		// Context of the app under test.
		Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

		Thread uiThread = XClientTest.getUIThread(context);
		Thread[] execThread = new Thread[1];

		Object lock = new Object();
		XClient.defaultClient(context)
			   .middleware(OkHttpMiddleware.middleware())
			   .request(r -> r
					   .uri("http://10.0.2.2/v2/items")
			   )
			   .onh("connected|disconnected", (client, object) -> {
				   execThread[0] = Thread.currentThread();

				   //PASS
				   Toast.makeText(client.context(), object.toString(), Toast.LENGTH_LONG).show();
				   System.out.println("LALA_LAND: " + object);

				   synchronized (lock) {
					   //noinspection NakedNotify
					   lock.notifyAll();
				   }
			   })
			   .on(Throwable.class, "exception|disconnected", (client, throwable) -> {
				   throwable.printStackTrace();
				   synchronized (lock) {
					   //noinspection NakedNotify
					   lock.notifyAll();
				   }
			   })
			   .connect();

		synchronized (lock) {
			//noinspection WaitNotInLoop,WaitOrAwaitWithoutTimeout,UnconditionalWait
			lock.wait();
		}
		Assert.assertSame("Code executed on different thread", uiThread, execThread[0]);
	}
}
