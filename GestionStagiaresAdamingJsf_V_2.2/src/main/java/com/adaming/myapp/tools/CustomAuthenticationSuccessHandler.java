package com.adaming.myapp.tools;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		final String studentTargetUrl = "/pages/etudiants/index.xhtml";
        final String adminTargetUrl = "/pages/admin/index.xhtml";
        final String formateurTargetUrl = "/pages/formateur/index.xhtml";
		
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect(request.getContextPath()+adminTargetUrl);
            LoggerConfig.logInfo("Authentication SUCCESS Role ADMIN");
        }
        else if(roles.contains("ROLE_ETUDIANT")) {
        	 response.sendRedirect(request.getContextPath()+studentTargetUrl);
        	 LoggerConfig.logInfo("Authentication SUCCESS Role ETUDIANT");
        }
        else if(roles.contains("ROLE_FORMATEUR")){
        	 response.sendRedirect(request.getContextPath()+formateurTargetUrl);
        	 LoggerConfig.logInfo("Authentication SUCCESS Role FORMATEUR");
        }
        
	}

}
