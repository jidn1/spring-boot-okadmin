import Vue from 'vue'
import Router from 'vue-router'

import footer from '@/components/common/Footer';
import header from '@/components/common/Header';

import player from '@/components/porn/Player';
import movieplayer from '@/components/moviePlayer';
import movie from '@/components/MovieList';
      import porn from '@/components/porn/mainPorn';


Vue.use(Router);
export default new Router({
  routes: [
    {
      path: '/',
      name: 'movie', // 首页
      components: {
        header: header,
        footer: footer,
        default: movie
      },
      meta: {
        title: '首页',
      }
    },
    {
      path: '/player',
      name: 'player', //
      components: {
        header: header,
        footer: footer,
        default: player
      },
      meta: {
        title: 'player',
      }
    },
    {
      path: '/movieplayer',
      name: 'movieplayer', //
      components: {
        header: header,
        footer: footer,
        default: movieplayer
      },
      meta: {
        title: 'movieplayer',
      }
    },
    {
      path: '/porn',
      name: 'porn', // porn
      components: {
        header: header,
        footer: footer,
        default: porn
      },
      meta: {
        title: 'porn',
      }
    }
  ]
})
