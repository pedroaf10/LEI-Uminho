function [ m ] = modelo1(c,x)
%c coeficientes do modelo
%x sao os dados 
%m = c(1).*x+c(2)*3*sin(x)+c(3)*2*x.^2;
m = c(1)*(x/100).^2;
end

