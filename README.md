# Considerações Gerais

Para esta etapa do processo - Desenvolver testes automatizados para a aplicação - as seguintes alterações foram feitas no projeto:

* A camada de apresentação foi refatorada para utilizar o padrão Model View Presenter ao invés do Model View View Model utilizado anteriormente
* A camada de dados foi simplificada para não guardar os dados localmente (Room), portanto nessa nova versão o aplicativo funciona apenas online
* As Activities foram reescritas utilizando a Linguagem Java, antes era utilizado Kotlin
* Como a camada de dados foi simplificada, o uso de um framework de injeção de dependências se tornou desnecessário

As alterações citadas foram realizadas para poder ter um aplicativo funcionando corretamente e focar os esforços no desenvolvimentos dos testes automatizados em um ambiente em que eu tenho mais experiência.

# Considerações sobre os testes

Apesar de ter sido comentado no item anterior que o framework de injeção de dependêcias foi removido do projeto, é sabido que ma classe que recebe suas dependências se torna mais testável, já que dessa forma podemos utilizar Mocks e métodos Stub para simular o comportamento de outras classes que podemos considerar que foram testadas e são confiáveis. Portanto inicialmente foi criado um novo flavour para a aplicação chamado **mock**, nesse flavour uma classe que faz o papel de injetor de dependências injeta as classes que necessitam de um mock específico.

## Teste dos Presenters
A vantagem da utilização de um arquitetura da camada de apresentação que separe a lógica (formatação, busca e tratamento dos dados) da exibição (interação com o framework Android) - como MVP - é que a camada encarregada pela lógica não precisa se preocupar com detalhes de implementação do framework Android. E testando-se apenas o Presenter o risco de a View apresentar um dado errado é pequeno, pois os métodos da View se encarregam apenas de fazer a interface entre o comando que recebeu, por exemplo, "mostra mensagem de erro" e a forma que o framework entende essa mensagem, digamos textView.setText(mensagem).

Logo os testes do Presenter se baseiam em fazer uma operação e verificar se a View recebeu o comando certo utilizando o framework **mockito** e o método **verify**. 

Uma outra ferramenta importante utilizada do **mockito** foi o **ArgumentCaptor** que facilita o mock das respostas às chamadas que retornam através de callback, utilizando essa ferramenta é possível dizer para uma determinada chamada retornar no callback de sucesso com determinada mensagem.

## Teste das Views
Para testar as Views foi utilizado o framework **Roboletric** que permite que os testes rodem na JVM sem a necessidade de um aparelho ou emulador.

Os testes da View se baseiam em chamar os métodos expostos por ela através do Contract e verificar se os elementos mostrados em tela (textos, cores, etc) correspondem ao esperado. Novamente percebe-se a vantagem da separação das responsabilidades entre a View e o Presenter, já que os testes da View podem focar apenas em verificar se os elementos estão apresentando a informação recebida da forma correta.

# Como rodar os testes
Para rodar os testes pelo Android Studio deve-se selecionar a buildVariant **mockDebug** e clicar com o botão direito no arquivo da classe e clicar em Run 'NomeClasseTest'. As classes de teste estão na pasta test e sob o mesmo pacote das implementações.
