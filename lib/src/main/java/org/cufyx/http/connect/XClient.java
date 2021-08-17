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
import org.cufy.http.connect.Action;
import org.cufy.http.connect.Callback;
import org.cufy.http.connect.Caller;
import org.cufy.http.connect.Client;
import org.cufy.http.middleware.Middleware;
import org.cufy.http.request.Request;
import org.cufy.http.syntax.HTTPRegExp;
import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * A modified {@link Client} for comfort in android.
 *
 * @param <B> the type of the request body.
 * @author LSafer
 * @version 0.0.1
 * @since 0.0.1 ~2021.04.08
 */
public interface XClient<B extends Body> extends Client<B> {
	/**
	 * <b>Default</b>
	 * <br>
	 * Return a new client instance to be a placeholder if a the user has not specified a
	 * client.
	 *
	 * @param context the context to be used by the client.
	 * @return a new default client.
	 * @throws NullPointerException if the given {@code context} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	static XClient<Body> client(@NotNull Context context) {
		return new AbstractXClient<>(context);
	}

	/**
	 * <b>Default</b>
	 * <br>
	 * Return a new client instance to be a placeholder if a the user has not specified a
	 * client.
	 *
	 * @param context the context to be used by the client.
	 * @param handler the handler to be used by the client.
	 * @return a new default client.
	 * @throws NullPointerException if the given {@code context} or {@code handler} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	static XClient<Body> client(@NotNull Context context, @NotNull Handler handler) {
		return new AbstractXClient<>(context, handler);
	}

	/**
	 * <b>Copy</b>
	 * <br>
	 * Construct a new copy of the given {@code client}.
	 *
	 * @param client the client to copy.
	 * @return a copy of the given {@code client}.
	 * @throws NullPointerException if the given {@code client} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	static Client<Body> client(@NotNull XClient<?> client) {
		return new AbstractXClient<>(client);
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new client with its request begin the given {@code request}.
	 *
	 * @param context the context to be used by the constructed client.
	 * @param request the request of this client.
	 * @return a new client from the given {@code request}.
	 * @throws NullPointerException if the given {@code context} or {@code request} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	static XClient<Body> client(@NotNull Context context, @NotNull Request<?> request) {
		return new AbstractXClient<>(context, request);
	}

	/**
	 * <b>Components</b>
	 * <br>
	 * Construct a new client with its request begin the given {@code request}.
	 *
	 * @param context the context to be used by the constructed client.
	 * @param handler the handler to be used by the constructed client.
	 * @param request the request of this client.
	 * @return a new client from the given {@code request}.
	 * @throws NullPointerException if the given {@code context} or {@code handler} or
	 *                              {@code request} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	static XClient<Body> client(@NotNull Context context, @NotNull Handler handler, @NotNull Request<?> request) {
		return new AbstractXClient<>(context, handler, request);
	}

	@NotNull
	@Override
	default XClient<B> connect() {
		return (XClient<B>) Client.super.connect();
	}

	@NotNull
	@Override
	default XClient<B> on(@NotNull @NonNls String regex, @NotNull Callback<Client<B>, Object> callback) {
		return (XClient<B>) Client.super.on(regex, callback);
	}

	@NotNull
	@Override
	default <T> XClient<B> on(@NotNull Class<T> type, @NotNull @NonNls String regex, @NotNull Callback<Client<B>, T> callback) {
		return (XClient<B>) Client.super.on(type, regex, callback);
	}

	@NotNull
	@Override
	default <T> XClient<B> on(@NotNull Callback<Client<B>, T> callback, @Nullable Action<T> @NotNull ... actions) {
		return (XClient<B>) Client.super.on(callback, actions);
	}

	@NotNull
	@Override
	default <T> XClient<B> ont(@NotNull Action<T> action, @NotNull Callback<Client<B>, T> callback) {
		return (XClient<B>) Client.super.ont(action, callback);
	}

	@NotNull
	@Override
	default XClient<B> ont(@NotNull @NonNls String regex, @NotNull Callback<Client<B>, Object> callback) {
		return (XClient<B>) Client.super.ont(regex, callback);
	}

	@NotNull
	@Override
	default <T> XClient<B> ont(@NotNull Class<T> type, @NotNull @NonNls String regex, @NotNull Callback<Client<B>, T> callback) {
		return (XClient<B>) Client.super.ont(type, regex, callback);
	}

	@NotNull
	@Override
	default <T> XClient<B> ont(@NotNull Callback<Client<B>, T> callback, @Nullable Action<T> @NotNull ... actions) {
		return (XClient<B>) Client.super.ont(callback, actions);
	}

	@NotNull
	@Override
	default <BB extends Body> XClient<BB> setRequest(@NotNull Request<BB> request) {
		return (XClient<BB>) Client.super.setRequest(request);
	}

	@NotNull
	@Override
	default XClient<Body> setRequest(@NotNull @NonNls @Pattern(HTTPRegExp.REQUEST) String request) {
		return (XClient<Body>) Client.super.setRequest(request);
	}

	@NotNull
	@Override
	default <BB extends Body> XClient<BB> request(@NotNull Function<Request<B>, Request<BB>> operator) {
		return (XClient<BB>) Client.super.request(operator);
	}

	/**
	 * Replace the context of this client to be the result of invoking the given {@code
	 * operator} with the current context of this client. If the {@code operator} returned
	 * null then nothing happens.
	 * <br>
	 * Throwable thrown by the {@code operator} will fall throw this method unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the context of this client cannot be
	 *                                       changed and the returned context from the
	 *                                       given {@code operator} is different from the
	 *                                       current context.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default XClient<B> context(@NotNull UnaryOperator<Context> operator) {
		Objects.requireNonNull(operator, "operator");
		Context c = this.getContext();
		Context context = operator.apply(c);

		if (context != null && context != c)
			this.setContext(context);

		return this;
	}

	/**
	 * Replace the handler of this client to be the result of invoking the given {@code
	 * operator} with the current handler of this client. If the {@code operator} returned
	 * null then nothing happens.
	 * <br>
	 * Throwable thrown by the {@code operator} will fall throw this method unhandled.
	 *
	 * @param operator the computing operator.
	 * @return this.
	 * @throws NullPointerException          if the given {@code operator} is null.
	 * @throws UnsupportedOperationException if the handler of this client cannot be
	 *                                       changed and the returned handler from the
	 *                                       given {@code operator} is different from the
	 *                                       current handler.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default XClient<B> handler(@NotNull UnaryOperator<Handler> operator) {
		Objects.requireNonNull(operator, "operator");
		Handler h = this.getHandler();
		Handler handler = operator.apply(h);

		if (handler != null && handler != h)
			this.setHandler(handler);

		return this;
	}

	/**
	 * Add the given {@code callback} to be performed in the UI thread (when possible)
	 * when the given {@code action} occurs.
	 * <br>
	 * Exceptions thrown by the given {@code callback} will be caught safely. But,
	 * exception by a thread created by the callback is left for the callback to handle.
	 *
	 * @param action   the action to listen to.
	 * @param callback the callback to be set.
	 * @param <T>      the type of the expected parameter.
	 * @return this.
	 * @throws NullPointerException if the given {@code action} or {@code callback} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_,_->this", mutates = "this")
	default <T> XClient<B> onh(@NotNull Action<T> action, @NotNull Callback<XClient<B>, T> callback) {
		Objects.requireNonNull(action, "action");
		Objects.requireNonNull(callback, "callback");
		return this.on(action, (caller, parameter) ->
				this.getHandler().post(() -> {
					try {
						callback.call((XClient<B>) caller, parameter);
					} catch (Throwable throwable) {
						this.trigger(Caller.EXCEPTION, throwable);
					}
				})
		);
	}

	/**
	 * Add the given {@code callback} to be performed in the UI thread (when possible)
	 * when the given {@code regex} occurs.
	 * <br>
	 * Exceptions thrown by the given {@code callback} will be caught safely. But,
	 * exception by a thread created by the callback is left for the callback to handle.
	 *
	 * @param regex    the regex to listen to.
	 * @param callback the callback to be set.
	 * @return this.
	 * @throws NullPointerException if the given {@code regex} or {@code callback} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_,_->this", mutates = "this")
	default XClient<B> onh(@NotNull @NonNls @Language("RegExp") String regex, @NotNull Callback<XClient<B>, Object> callback) {
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(callback, "callback");
		return this.on(Object.class, regex, (caller, parameter) ->
				this.getHandler().post(() -> {
					try {
						callback.call((XClient<B>) caller, parameter);
					} catch (Throwable throwable) {
						this.trigger(Caller.EXCEPTION, throwable);
					}
				})
		);
	}

	/**
	 * Add the given {@code callback} to be performed in the UI thread (when possible)
	 * when the given {@code regex} occurs.
	 * <br>
	 * Exceptions thrown by the given {@code callback} will be caught safely. But,
	 * exception by a thread created by the callback is left for the callback to handle.
	 *
	 * @param type     the type of the accepted parameters.
	 * @param regex    the regex to listen to.
	 * @param callback the callback to be set.
	 * @param <T>      the type of the expected parameter.
	 * @return this.
	 * @throws NullPointerException if the given {@code type} or {@code regex} or {@code
	 *                              callback} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_,_,_->this", mutates = "this")
	default <T> XClient<B> onh(@NotNull Class<T> type, @NotNull @NonNls @Language("RegExp") String regex, @NotNull Callback<XClient<B>, T> callback) {
		Objects.requireNonNull(type, "type");
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(callback, "callback");
		return this.on(type, regex, (caller, parameter) ->
				this.getHandler().post(() -> {
					try {
						callback.call((XClient<B>) caller, parameter);
					} catch (Throwable throwable) {
						this.trigger(Caller.EXCEPTION, throwable);
					}
				})
		);
	}

	/**
	 * Add the given {@code callback} to be performed in the UI thread (when possible)
	 * when any of the given {@code actions} occurs.
	 * <br>
	 * Exceptions thrown by the given {@code callback} will be caught safely. But,
	 * exception by a thread created by the callback is left for the callback to handle.
	 * <br>
	 * Null elements in the given {@code actions} array will be skipped.
	 *
	 * @param callback the callback to be set.
	 * @param actions  the actions to listen to.
	 * @param <T>      the type of the expected parameter.
	 * @return this.
	 * @throws NullPointerException if the given {@code callback} or {@code actions} is
	 *                              null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_,_->this", mutates = "this")
	default <T> XClient<B> onh(@NotNull Callback<XClient<B>, T> callback, @Nullable Action<T> @NotNull ... actions) {
		Objects.requireNonNull(callback, "callback");
		Objects.requireNonNull(actions, "actions");
		for (Action<T> action : actions)
			if (action != null)
				this.onh(action, callback);

		//noinspection unchecked
		return this;
	}

	/**
	 * Set the context used by this to the given {@code context}.
	 *
	 * @param context the new context to be set.
	 * @return this.
	 * @throws NullPointerException if the given {@code context} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default XClient<B> setContext(@NotNull Context context) {
		throw new UnsupportedOperationException("context");
	}

	/**
	 * Set the handler used by this to the given {@code handler}.
	 *
	 * @param handler the new handler to be set.
	 * @return this.
	 * @throws NullPointerException if the given {@code handler} is null.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(value = "_->this", mutates = "this")
	default XClient<B> setHandler(@NotNull Handler handler) {
		throw new UnsupportedOperationException("handler");
	}

	@NotNull
	@Override
	XClient<B> clone();

	@NotNull
	@Override
	XClient<B> middleware(@NotNull Middleware<? super Client<B>> middleware);

	@NotNull
	@Override
	<T> XClient<B> on(@NotNull Action<T> action, @NotNull Callback<Client<B>, T> callback);

	@NotNull
	@Override
	<T> XClient<B> trigger(@NotNull Action<T> action, @Nullable T parameter);

	@NotNull
	@Override
	XClient<B> trigger(@NotNull @NonNls String trigger, @Nullable Object parameter);

	@NotNull
	@Override
	<T> XClient<B> trigger(@Nullable T parameter, @Nullable Action<T> @NotNull ... actions);

	@NotNull
	@Override
	XClient<B> trigger(@Nullable Object parameter, @Nullable @NotNull String @NotNull ... triggers);

	/**
	 * Get the context assigned to this client.
	 *
	 * @return the context of the client.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(pure = true)
	Context getContext();

	/**
	 * Get the handler used by this client.
	 *
	 * @return the handler of the client.
	 * @since 0.0.1 ~2021.04.08
	 */
	@NotNull
	@Contract(pure = true)
	Handler getHandler();
}
