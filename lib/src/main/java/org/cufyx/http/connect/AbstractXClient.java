/*
 *	Copyright 2021 Cufy
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufyx.http.connect;

import android.content.Context;
import android.os.Handler;

import org.cufy.http.body.Body;
import org.cufy.http.connect.AbstractClient;
import org.cufy.http.connect.Action;
import org.cufy.http.connect.Callback;
import org.cufy.http.connect.Client;
import org.cufy.http.middleware.Middleware;
import org.cufy.http.request.Request;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A modified {@link org.cufy.http.connect.Client client} for comfort in android.
 *
 * @param <B> the type of the body of the request of this client.
 * @author LSafer
 * @version 0.0.1
 * @since 0.0.1 ~2021.03.23
 */
public class AbstractXClient<B extends Body> extends AbstractClient<B> implements XClient<B> {
	/**
	 * The context used by the client.
	 *
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	protected Context context;
	/**
	 * The handler used to run callbacks in the ui thread with.
	 *
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	protected Handler handler;

	/**
	 * <b>Default</b>
	 * <br>
	 * Construct a new default context client.
	 *
	 * @param context the context to be used by the client.
	 * @throws NullPointerException if the given {@code context} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context) {
		Objects.requireNonNull(context, "context");
		this.context = context;
		this.handler = new Handler(context.getMainLooper());
	}

	/**
	 * <b>Default</b>
	 * <br>
	 * Construct a new default context client.
	 *
	 * @param context the context to be used by the client.
	 * @param handler the handler to be used by the client.
	 * @throws NullPointerException if the given {@code context} or {@code handler} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context, @NotNull Handler handler) {
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(handler, "handler");
		this.context = context;
		this.handler = handler;
	}

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new copy of the given {@code client}.
	 *
	 * @param client the client to copy.
	 * @throws NullPointerException if the given {@code client} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull XClient<?> client) {
		super(client);
		this.context = client.context();
		this.handler = client.handler();
	}

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new copy of the given {@code client}.
	 *
	 * @param context the context to be used by the client.
	 * @param client  the client to copy.
	 * @throws NullPointerException if the given {@code context} or {@code client} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context, @NotNull Client<?> client) {
		super(client);
		Objects.requireNonNull(context, "context");
		this.context = context;
		this.handler = new Handler(context.getMainLooper());
	}

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new copy of the given {@code client}.
	 *
	 * @param context the context to be used by the client.
	 * @param handler the handler to be used by the client.
	 * @param client  the client to copy.
	 * @throws NullPointerException if the given {@code context} or {@code handler} or
	 *                              {@code client} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context, @NotNull Handler handler, @NotNull Client<?> client) {
		super(client);
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(handler, "handler");
		this.context = context;
		this.handler = handler;
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new client with its request begin the given {@code request}.
	 *
	 * @param context the context to be used by the client.
	 * @param request the request of this client.
	 * @throws NullPointerException if the given {@code context} or {@code request} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context, @NotNull Request<?> request) {
		super((Request)/*fixme remove cast*/ request);
		Objects.requireNonNull(context, "context");
		this.context = context;
		this.handler = new Handler(context.getMainLooper());
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new client with its request begin the given {@code request}.
	 *
	 * @param context the context to be used by the client.
	 * @param handler the handler to be used by the client.
	 * @param request the request of this client.
	 * @throws NullPointerException if the given {@code context} or {@code request} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	public AbstractXClient(@NotNull Context context, @NotNull Handler handler, @NotNull Request<?> request) {
		super((Request)/*fixme remove cast*/ request);
		Objects.requireNonNull(context, "context");
		Objects.requireNonNull(handler, "handler");
		this.context = context;
		this.handler = handler;
	}

	@NotNull
	@Override
	public AbstractXClient<B> clone() {
		return (AbstractXClient<B>) super.clone();
	}

	@NotNull
	@Override
	public XClient<B> context(@NotNull Context context) {
		Objects.requireNonNull(context, "context");
		this.context = context;
		return this;
	}

	@NotNull
	@Override
	public Context context() {
		return this.context;
	}

	@Override
	@NotNull
	public XClient<B> handler(@NotNull Handler handler) {
		Objects.requireNonNull(handler, "handler");
		this.handler = handler;
		return this;
	}

	@NotNull
	@Override
	public Handler handler() {
		return this.handler;
	}

	@NotNull
	@Override
	public XClient<B> middleware(@NotNull Middleware<? super Client<B>> middleware) {
		return (XClient<B>) super.middleware(middleware);
	}

	@NotNull
	@Override
	public <T> XClient<B> on(@NotNull Action<T> action, @NotNull Callback<Client<B>, T> callback) {
		return (XClient<B>) super.on(action, callback);
	}

	@NotNull
	@Override
	public <BB extends Body> XClient<BB> request(@NotNull Request<BB> request) {
		return (XClient<BB>) super.request(request);
	}

	@NotNull
	@NonNls
	@Override
	public String toString() {
		return "XClient " + System.identityHashCode(this);
	}

	@NotNull
	@Override
	public <T> XClient<B> trigger(@NotNull Action<T> action, @Nullable T parameter) {
		return (XClient<B>) super.trigger(action, parameter);
	}

	@NotNull
	@Override
	public XClient<B> trigger(@NotNull String trigger, @Nullable Object parameter) {
		return (XClient<B>) super.trigger(trigger, parameter);
	}

	@NotNull
	@Override
	public XClient<B> trigger(@Nullable Object parameter, @Nullable String @NotNull ... triggers) {
		return (XClient<B>) super.trigger(parameter, triggers);
	}

	@NotNull
	@Override
	public <T> XClient<B> trigger(@Nullable T parameter, @Nullable Action<T> @NotNull ... actions) {
		return (XClient<B>) super.trigger(parameter, actions);
	}
}
