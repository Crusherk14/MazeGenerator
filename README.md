# MazeGenerator
##Краткое описание

![alt tag](http://s020.radikal.ru/i717/1606/79/4b0b4c267580.png)

![alt tag](http://i.giphy.com/l41YqG1gZOa5gxi3C.gif)

MazeGenerator - Модуль процедурной генерации лабиринтов с единственным решением в двухмерном пространстве, осуществляющих визуализированное построение двухмерного лабиринта в реальном времени согласно заданным параметрам с возможностью последующего импорта/экспорта в различных форматах. 

Данный продукт может быть использован как в качестве тренировочной площадки при разработке искусственного интеллекта, так и в различных медицинских, инженерных и игровых сферах, так как является не только самостоятельным продуктом, позволяющим экспортировать результаты работы в различных форматах, но и компонентом с возможностью дальнейшего использования в разработке любого программного продукта.

##Инструкция по запуску

Исполняемый файл MazeGenerator.jar находится в корне репозитория. Для запуска потребуется установленная на компьютере актуальная версия Java. Для начала генерации лабиринта в реальном времени необходимо нажать на кнопку Generate.

##Пользовательский интерфейс

Пользовательский интерфейс MazeGenerator позволяет непосредственно перед началом процесса генерации изменить масштаб отображения (Tile Size), размер генерируемого лабиринта (Width и Height), а также незначительно повлиять на сложность генерируемых ответвлений (Path Length). Параметр "Generation Delay" позволяет настроить скорость генерации лабиринта в режиме реального времени. Пункты "Black&White Mode" и "Show Answer" позволяют активировать чёрно-белый режим отображения, а также выделение решения лабиринта соответственно.

![alt tag](http://s017.radikal.ru/i426/1606/1a/43c31e7724e4.png)

В чёрно-белом режиме белым цветом будут отображены клетки, по которым можно перемещаться, а серым - препятствия.
В цветном режиме каждое состояние клетки будет отражено соответствующим цветом. Варианты состояний:
Start (Вход в лабиринт) [Зелёный], 
Finish (Выход из лабиринта) [Оранжевый],
Empty (Пустая клетка) [Белый], 
Wall (Стена) [Серый], 
HWall (Высокая стена) [Тёмно-Серый], 
Path (Проходная клетка) [Синий], 
Turn (Проходной изгиб) [Синий], 
Cross (Перекрёсток) [Светло-Пурпурный].

![alt tag](http://s017.radikal.ru/i418/1606/65/723b0f23b8f5.png)

Внизу панели инструментов можно обнаружить кнопки загрузки и сохранения полученных результатов. Производить сохранение и загрузку лабиринтов возможно в любой момент времени, однако функция отображения решения лабиринта в загруженных лабиринтах доступна не будет.

##Подробное описание алгоритма

Отображение лабиринта производится путём присвоения ячейкам массива различных состояний. Графическое отображение позволяет пользователю наблюдать структуру массива посредством окрашивания ячеек в соответствующие состояниями в массиве цвета.

После получения начальных параметров генерации алгоритм создаёт массив заданных размеров и заполняет границы массива состояниями Wall.
Случайным образом на площади массива выбирается начальная клетка, которой присваивается состояние Start. Методом generatePath из данной точки генерируется базовый маршрут(в дальнейшем Путь), который и станет единственным решением лабиринта. Конечной клетке данного пути присваивается состояние Finish.

Дальнейшие операции выполняются рекурсивно вплоть до невозможности дальнейшего построения:
На полученном маршруте отмечаются клетки, на которых в текущий момент возможно образование дополнительных путей (в дальнейшем Ответвление). Вероятность получения клеткой на маршруте данного состояния Cross: увеличивается по мере удаления от последнего ответвления, а также увеличивается на 10%, если данная клетка является изгибом пути (Turn).Все полученные клетки являются точками начала генерации новых путей. Длина нового пути может быть как меньше родительского, так и больше.

Каждый шаг алгоритма, запущенного в отдельном потоке, сопровождается обновлением пользовательского интерфейса. Каждое обновление представляет собой окраску ячеистой структуры в цвета, соответствующие состояниям лабиринта.

Выполнение алгоритма происходит по относительно случайному принципу. Во время принятия алгоритмом всех решений о выборе между различными вариантами, каждому варианту присваивается некоторый процент вероятности. С наибольшей вероятностью алгоритмом будет выбран варинт с наибольшим относительным процентом вероятности, однако элемент гарантированности исключается. Таким образом, каждая генерация лабиринта является неповторяющейся.
