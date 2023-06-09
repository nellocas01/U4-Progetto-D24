package com.example.u4Progettod24.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.u4Progettod24.entities.Utente;
import com.example.u4Progettod24.entities.UtenteService;
import com.example.u4Progettod24.exceptions.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UtenteService utenteService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");

		String accessToken = authHeader.substring(7);

		JWTTools.isTokenValid(accessToken);

		String email = JWTTools.extractSubject(accessToken);
		System.out.println("******************************** " + email);
		try {
			Utente utente = utenteService.findByEmail(email);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(utente, null,
					utente.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}