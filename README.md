# Arquitetura e Linguagem escolhidas
A linguagem escolhida para implementação foi Kotlin e a arquitetura 
a proposta pelo [Google](https://developer.android.com/topic/libraries/architecture/guide).

Uma arquitetura em que há apenas uma fonte de dados que o app lê, apesar de poder haver mais de uma fonte de 
dados que o app consulta. Na implementação atual o app lê do banco de dados local [Room](https://developer.android.com/topic/libraries/architecture/room) 
e consulta da [API](https://www.themoviedb.org/). Essa decisão foi tomada pois um dos requisitos era que o dado funcionasse no modo OFFLINE.

A arquitetura da camada de apresentação escolhida foi MVVM (também proposta pelo Google no artigo anterior). 
Porém ao invés de utilizar [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) nas camadas mais baixas
foi utilizado [RxJava](https://github.com/ReactiveX/RxJava) na intenção de não deixar classes específicas da plataforma
Android espalhadas por todas as camadas.

Essa arquitetura foi escolhida pois, além de ser a recomendação do Google, um projeto desenvolvido utilizando
a arquitetura MVP pode ser encontrado [aqui](https://github.com/aaascp/gerenciador_pedidos).

# Ausência de testes na aplicação
Esse foi o segundo projeto que fiz utilizando a arquitetura com MVVM, portanto não tive tempo hábil para
deixar tudo funcionando e ainda aprender a testar esse tipo de arquitetura. Porém, novamente, testes
de unidade na arquitetura podem ser encontrados [nesse projeto](https://github.com/aaascp/gerenciador_pedidos). 
Que testa tanto os presenters, quanto as views(utilizando [roboletric](http://robolectric.org/)).

# Considerações finais
A aplicação não está funcionando redonda do jeito que gostaria, pois, como já comentado, tenho pouca expriência 
na arquitetura MVVM. Porém mesmo apesar da dificuldade eu decidi fazer dessa maneira, já que um projeto realizado
 na arquitetura  onde tenho mais experiência pode ser encontrado no link já citado anteriormente.

# Download da aplicação
O aplicativo pode ser baixado no celular através [desse link](https://drive.google.com/file/d/1_ip3PpC2FonN9cIo8BHgNgAeM11ufxhU/view?usp=sharing).
