package com.tailoredshapes.inventoryserver.filters;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.tailoredshapes.inventoryserver.extractors.IdExtractor;
import com.tailoredshapes.inventoryserver.parsers.Parser;
import com.tailoredshapes.inventoryserver.repositories.Repository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Singleton
public class TFilter<T> implements Filter {

    private final Parser<T> parser;
    private final IdExtractor<T> extractor;
    private final Repository<T> repository;
    private final Class type;
    private final String parameterName;

    @Inject
    public TFilter(Parser<T> parser, IdExtractor<T> extractor, Repository<T> repository, Class type, String parameterName) {
        this.parser = parser;
        this.extractor = extractor;
        this.repository = repository;
        this.type = type;
        this.parameterName = parameterName;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        T t = null;
        if (httpRequest.getParameterMap().containsKey(parameterName)) {
            String tJson = httpRequest.getParameter(parameterName);
            t = parser.parse(tJson);
        } else {
            Long extract = extractor.extract(((HttpServletRequest) request).getRequestURI());
            if (extract != null) {
                t = repository.findById(extract);
                if (t == null) {
                    throw new RuntimeException(String.format("No %s with id %d", type, extract));
                }
            }
        }

        httpRequest.setAttribute(
                Key.get(type,
                        Names.named("current_" + parameterName)).toString(),
                t);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
